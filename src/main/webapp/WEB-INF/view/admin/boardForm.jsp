<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>
        <style>

        </style>


        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container my-3">
                    <div class="list-group">
                        <a href="/admin/userForm" class="list-group-item list-group-item-action">
                            회원관리
                        </a>
                        <a href="/admin/boardForm" class="list-group-item list-group-item-action active"
                            aria-current="true">게시글 관리</a>
                        <a href="/admin/replyForm" class="list-group-item list-group-item-action">댓글 관리</a>
                    </div>
                </div>

                <div class="vl">

                </div>

            </div>

            <div class="d-flex" style="position: absolute; top:30px; left: 220px">
                <table class="table">
                    <thead>
                        <tr class="my-text-align">
                            <th scope="col">#</th>
                            <th scope="col">게시글번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">내용</th>
                            <th scope="col">작성자</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${boardList}" var="board">
                            <tr class="my-text-align">
                                <th scope="row"></th>
                                <td>${board.id}</td>
                                <td>${board.title}</td>
                                <td>${board.content}</td>
                                <td>${board.username}</td>
                                <td><button onclick="deleteById(`${board.id}`)" class="btn-xs">삭제</button>
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
                    url: "/admin/board/delete/" + id,
                    dataType: "json"
                }).done((res) => {
                    alert(res.msg);
                    location.href = "/admin/boardForm";
                }).fail((err) => {
                    alert(err.responseJSON.msg);
                });
            }
        </script>
        <%@ include file="../layout/footer.jsp" %>