<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>
        <style>

        </style>


        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container mt-5">
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

            <div class="d-flex justify-content-center">
                <div style="position: absolute; top: 50px">
                    <table class="table">
                        <thead>
                            <tr class="my-text-align">
                                <th scope="col">#</th>
                                <th scope="col">게시글번호</th>
                                <th scope="col">제목</th>
                                <th scope="col">내용</th>
                                <th scope="col">작성자</th>
                                <th scope="col">작성일자</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>

                        <tbody id="searchArea">
                            <c:forEach items="${boardList}" var="board">
                                <tr class="my-text-align">
                                    <th scope="row"></th>
                                    <td>${board.id}</td>
                                    <td>${board.title}</td>
                                    <td>${board.content}</td>
                                    <td>${board.username}</td>
                                    <td>${board.createdAtToString}</td>
                                    <td><button onclick="deleteById(`${board.id}`)" class="btn-xs">삭제</button>
                                    </td>
                                </tr>
                            </c:forEach>

                        </tbody>

                    </table>

                </div>
            </div>
        </div>
        <div class="mx-quto input-group justify-content-center" style="position:absolute; bottom: 300px">
            <div>
                <mx-auto>
                    <input id="boardSearch" name="query" type="text" class="form-control" placeholder="검색어 입력"
                        aria-label="search" value="" aria-describedby="button-addon2">
                </mx-auto>
            </div>
            <div>
                <button id="button-addon2" class="btn btn-primary" type="submit" onclick="serachGet()">검색</button>
            </div>
        </div>

        <script>



            function serachGet() {
                let search = $("#boardSearch").val();
                $.ajax({
                    type: "get",
                    url: "/admin/search/board/?serachKeyword=" + search,
                    dataType: "json"
                }).done((res) => {
                    $("#searchArea").empty();

                    // console.log(res[0].id);
                    // console.log(res[0].title);
                    // console.log(res[0].content);
                    // console.log(res[0].username);
                    // console.log(res[0].createdAt);

                    for (let i = 0; i < res.length; i++) {
                        let id = res[i].id;
                        let title = res[i].title;
                        let content = res[i].content;
                        let username = res[i].username;
                        let createdAt = res[i].createdAt;

                        let searchRes = `
                        <tr class= "my-text-align" >
                                <th scope="row"></th>
                                <td>`+ id + `</td>
                                <td>`+ title + `</td>
                                <td>`+ content + `</td>
                                <td>`+ username + `</td>
                                <td>`+ createdAt + `</td>
                                <td><button onclick="deleteById(`+ id + `)" class="btn-xs">삭제</button>
                                </td>
                            </tr >
                        `;

                        $("#searchArea").append(searchRes);
                    }

                }).fail((err) => {
                    alert(err.responseJSON.msg);
                });

            }

            $("#boardSearch").keyup(() => {
                serachGet();
            })


            $("#boardSearch").keypress(function (e) {
                if (e.keyCode === 13) {
                    serachGet();
                }
            });

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