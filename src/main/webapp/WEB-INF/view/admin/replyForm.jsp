<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>
        <style>
            .vl {
                border-left: 1px solid grey;
                height: 723px;
                margin-left: 10px;
            }
        </style>


        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container my-3">
                    <div class="list-group">
                        <a href="/admin/userForm" class="list-group-item list-group-item-action">
                            회원관리
                        </a>
                        <a href="/admin/boardForm" class="list-group-item list-group-item-action">게시글 관리</a>
                        <a href="/admin/replyForm" class="list-group-item list-group-item-action active" aria-current="true">댓글 관리</a>
                    </div>
                </div>

                <div class="vl">

                </div>

            </div>

            <div class="d-flex" style="position: absolute; top:30px; left: 220px">
            <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">댓글 번호</th>
                            <th scope="col">게시글 번호</th>
                            <th scope="col">작성자</th>
                            <th scope="col">댓글 내용</th>
                            <th scope="col"></th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${replyList}" var="reply">
                            <tr class="my-text-align">
                                <th scope="row"></th>
                                <td>${reply.id}</td>
                                <td>${reply.boardId}</td>
                                <td>${reply.username}</td>
                                <td>${reply.comment}</td>
                                <td><button onclick="deleteById(`${reply.id}`)" class="btn-xs">삭제</button>
                                </td>
                            </tr>

                        </c:forEach>


                    </tbody>
                </table>
            </div>

        </div>
        <script>
            function deleteById(id) {
                $.ajax({
                    type: "delete",
                    url: "/admin/reply/delete/" + id,
                    dataType: "json"
                }).done((res) => {
                    alert(res.msg);
                    location.href = "/admin/replyForm";
                }).fail((err) => {
                    alert(err.responseJSON.msg);
                });
            }
        </script>
        <%@ include file="../layout/footer.jsp" %>