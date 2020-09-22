<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <title>Registration</title>
</head>
<body>
<style>
    body {
        padding-top: 60px;
    }

    fieldset {
        text-align: center;
    }

    input[type=email], input[type=password] {
        border: 1px solid #ccc;
        border-radius: 50px;
    }

    input[type=submit] {
        margin-left: 120px;
    }

</style>
<div class="container-sm">
    <div class="row">
        <div class="col">
        </div>
        <div class="col-4">
            <form name="registrationForm" method="post" action="controller" class="was-validated">
                <fieldset disabled>
                    <legend>Create your account</legend>
                </fieldset>
                <input type="hidden" name="command" value="registration"/>
                <div class="mb-3">
                    <div class="form-group">
                        <label for="exampleInputEmail1">Login</label>
                        <input type="email" name="email"
                               class="form-control is-invalid"
                               id="exampleInputEmail1"
                               aria-describedby="validatedInputGroupPrepend"
                               pattern="^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
                               placeholder="Enter your Email address"
                               required>
                    </div>
                </div>
                ${registrationErrorLogin}<br/>
                <div class="mb-3">
                    <div class="form-group">
                        <label for="exampleInputPassword1">Password</label>
                        <input type="password" name="password"
                               class="form-control is-invalid"
                               id="exampleInputPassword1"
                               aria-describedby="validatedInputGroupPrepend"
                               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$"
                               placeholder="Must be 6-20 characters long"
                               required>
                        <small id="passwordHelp" class="form-text text-muted">
                            The password must consist of large and small letters and numbers</small>
                    </div>
                </div>
                <div class="mb-3">
                    <div class="form-group">
                        <label for="exampleInputPassword">Confirm password</label>
                        <input type="password" name="repeat password"
                               class="form-control is-invalid"
                               id="exampleInputPassword"
                               aria-describedby="validatedInputGroupPrepend"
                               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$"
                               placeholder="Confirm your password"
                               required>
                    </div>
                </div>
                ${wrongAction}
                ${nullPage}
                ${registrationError}<br/><br/>
                <input class="btn btn-outline-primary" type="submit" value="Submit">
            </form>
        </div>
        <div class="col">
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>
