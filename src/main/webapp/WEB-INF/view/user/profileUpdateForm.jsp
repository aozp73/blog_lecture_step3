<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>

        <style>
            .container {
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            h2 {
                margin-top: 2rem;
            }

            form {
                width: 50%;
                margin-top: 2rem;
                display: flex;
                flex-direction: column;
                align-items: center;
                border: 1px solid gray;
                padding: 1rem;
                border-radius: 10px;
            }

            .form-group {
                margin-bottom: 1rem;
                text-align: center;
            }

            .form-group img {
                width: 320px;
                height: 270px;
                border-radius: 50%;
                margin-bottom: 1rem;
                border: 1px solid gray;
            }

            .btn {
                margin-top: 1rem;
                width: 20%;
            }
        </style>

        <div class="container my-3">
            <h2 class="text-center">프로필 사진 변경 페이지 </h2>

            <form id="profileForm" method="post" action="/user/profileUpdate" enctype="multipart/form-data">
                <div class="form-group">
                    <img id="imagePreview" src="${user.profile == null ? "/images/dora.png" : user.profile}" alt="Current Photo" class="img-fluid">
                </div>
                <div class="form-group">
                    <input type="file" class="form-control" id="profile" name="profile" onchange="chooseImage(this)">
                </div>
                <button type="submit" class="btn btn-primary">사진변경</button>
            </form>

        </div>

        <script>
            function chooseImage(obj) {
                // console.log(obj);
                // console.log(obj.files);
                let f = obj.files[0];
                // console.log(f);

                // image -> mime type
                if(!f.type.match("image.*")){
                    alert("이미지를 등록해야 합니다.");
                    return;
                }

                let reader = new FileReader();
                reader.readAsDataURL(f);

                // Call Back 함수
                // 콜스택이 다 비워지고, 이벤트 루프로 가서 readAsDataURL 이벤트가 끝나면(Pending) 콜백 시켜주는 함수
                reader.onload = function (e) {
                    console.log(e);
                    console.log(e.target.result);
                    $("#imagePreview").attr("src", e.target.result);
                }
            }

            // ajax
            function updateImage(){
                // 하나밖에 없는데 0번지에 들어가 있음 0번지 뽑으면 모든 정보 뽑는 것
                let profileForm = $("#profileForm")[0];
                // FormData안에 Form태그에 대한 모든 정보가 들어가있음
                let formData = new FormData(profileForm);

                $.ajax({
                    type: "put",
                    url: "/user/profileUpdate",
                    data: formData,
                    contentType: false, // 필수: x-www-form-urlencoded로 파싱되는 것을 방지
                                        // x-www로 인식하면 사진, x-www로 못 찢음
                    processData: false, // 필수: contentType을 false로 줬을 때 QueryString 자동 설정 됨. 해제
                                        // contentType이 false면 Body가 없다고 인식하고 QueryString으로 파싱하려고 함 
                    enctype: "multipart/form-data",
                    dataType: "json"
                }).done((res) => {
                    alert(res.msg);
                    location.href = "/";
                }).fail((err)=>{
                    alert(err.responseJSON.msg);
                });
            }

        </script>

        <%@ include file="../layout/footer.jsp" %>