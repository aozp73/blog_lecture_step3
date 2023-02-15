<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>

        <div class="d-flex justify-content-center pt-3">
            <div>
                <h3>관리자 로그인 페이지</h3>
            </div>
        </div>
        <hr>
        <div class="container my-3">
            <div class="container">
                <form action="/admin/login" method="post">
                    <div class="form-group mb-2">
                        <input type="text" name="username" class="form-control" placeholder="Enter username"
                            id="username">
                    </div>

                    <div class="form-group mb-2">
                        <input type="password" name="password" class="form-control" placeholder="Enter password"
                            id="password">
                    </div>

                    <button type="submit" class="btn btn-primary">로그인</button>
                </form>

            </div>
        </div>

        <%@ include file="../layout/footer.jsp" %>