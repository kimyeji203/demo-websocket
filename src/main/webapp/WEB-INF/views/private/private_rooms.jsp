<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-aFq/bzH65dt+w6FI2ooMVUpc+21e0SRygnTpmBvdBgSdnuTN7QbdgL+OapgHtvPp" crossorigin="anonymous">
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>

<script>

    $(document).ready(function () {

        // ui init ---------------------------------
        initRoomList();

        // mapping event ---------------------------
        $("#btn-create").on("click", () => {
            callCreateRoom()
                .then((response) => {
                    $("#name").val("");
                    initRoomList();
                });
        });

    });


    // function ------------------------------------
    function initRoomList() {
        $.ajax({
            url: "/room",
            type: "get",
            contentType: "application/json; charset=utf-8"
        }).then((response) => {
            $("#rooms > ul > li").remove();

            const roomList = response;
            roomList.forEach(room => {
                const id = room.roomId;
                const name = room.roomName;

                $("#rooms > ul").append(`
                    <li class="col-12 room" room-id="` + id + `">
                       <a href="/private/room/chat?id=` + id + `">` + name + `</a>
                    </li>
                `);
            });
        });
    }


    function callCreateRoom() {
        const name = $("#room-name").val();
        return $.ajax({
            url: "/room",
            type: "post",
            data: name,
            contentType: "application/json; charset=utf-8"
        });
    }
</script>

<body>
<div class="container">
    <div class="col-12" style="text-align: center;">
        <h3><b>채팅방 목록</b></h3>
    </div>
    <div class="col-12">
        <div class="input-group mb-3">
            <input type="text" id="room-name" class="form-control" placeholder="Enter room name"
                   style="margin-right: 10px">
            <div class="input-group-append">
                <button class="btn btn-primary" type="button" id="btn-create">개설</button>
            </div>
        </div>
    </div>
    <div>
        <div class="col-12" id="rooms">
            <ul></ul>
        </div>
    </div>
</div>
</body>
</html>