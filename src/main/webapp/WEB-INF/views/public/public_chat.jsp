<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
</head>

<script>
    let websocket = null;
    let name = null;

    $(document).ready(function () {

        // init ui --------------------------------
        $("#name").attr("disabled", false);
        $("#button-open").text("입장");
        $("#div-send").hide();


        // mapping event --------------------------
        // 메시지 전송
        $("#button-send").on("click", (e) => {
            send("TEXT");
        });

        // 입장 or 퇴장
        $("#button-open").on("click", (e) => {
            if (websocket == null) {

                name = $("#name").val();
                if (name == null) {
                    alert("이름을 먼저 입력해주세요~");
                    return;
                }

                connection();
            } else {
                disconnect();
            }
        });
    });


    // function ------------------------------------

    function connection()
    {
        // websocket = new WebSocket("ws://localhost:8080/publicChat?name=" + name);
        websocket = new SockJS("/publicChat?name=" + name,
            null,
            {
                transports: ["websocket", "xhr-streaming", "xhr-polling"]
            });
        websocket.onmessage = onMessage;
        websocket.onopen = onOpen;
        websocket.onclose = onClose;
    }

    function disconnect()
    {
        websocket.close();
        onClose(null);
    }

    // 웹소켓 메시지 전송
    function send(type) {
        if (websocket == null) {
            window.alert("채팅방 입장 먼저 해주세요~");
            return;
        }

        let msg = document.getElementById("msg");
        const payload = {
            type: type,
            context: msg.value
        };

        websocket.send(JSON.stringify(payload));
        msg.value = '';
    }

    // 입장
    function onOpen(evt) {
        send("OPEN");

        $("#name").attr("disabled", true);
        $("#button-open").text("퇴장");
        $("#div-send").show();
    }

    // 퇴장
    function onClose(evt) {
        websocket = null;

        $("#name").attr("disabled", false);
        $("#button-open").text("입장");
        $("#div-send").hide();
    }

    // 메시지 수신
    function onMessage(msg) {

        const jsonMsg = JSON.parse(msg.data);
        const type = jsonMsg.type;
        const message = jsonMsg.context;
        const isMine = jsonMsg.isMine;

        let divClass;
        let divStyle;


        if (type === "TEXT" && isMine) {
            divClass = 'col-6 alert alert-warning';
            divStyle = 'margin-left:50%';
        } else if (type === "TEXT" && isMine === false) {
            divClass = 'col-6 alert alert-primary';
            divStyle = 'right-left:50%';
        } else {
            divClass = 'col-12';
            divStyle = 'text-align:center; color:darkgray;';
        }

        // 내가 보낸 채팅
        let html = `
            <div class='col-12'>
                <div class='` + divClass + `' style='` + divStyle + `'>
                    <b>` + message + `</b>
                </div>
            </div>
        `;
        $("#msgArea").append(html);
    }
</script>

<body>
<div class="container">
    <div class="col-12" style="text-align: center;">
        <h3><b>전체 채팅방</b></h3>
    </div>
    <div class="col-12">
        <div class="input-group mb-3">
            <input type="text" id="name" class="form-control" placeholder="Enter your name" style="margin-right: 10px">
            <div class="input-group-append">
                <button class="btn btn-primary" type="button" id="button-open">입장</button>
            </div>
        </div>
    </div>
    <div>
        <div id="msgArea" class="col" style="min-height: 500px; max-height: 100vh; overflow: scroll;">

        </div>
        <div class="col-12" id="div-send">
            <div class="input-group mb-3">
                <input type="text" id="msg" class="form-control" placeholder="Enter message"
                       style="margin-right: 10px;">
                <div class="input-group-append">
                    <button class="btn btn-warning" type="button" id="button-send">전송</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>