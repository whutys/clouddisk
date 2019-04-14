<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>注册</title>

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <link rel="stylesheet" href="static/css/bootstrap.css"/>
    <link rel="stylesheet" href="static/css/bootstrapValidator.css"/>

    <script type="text/javascript" src="static/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="static/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="static/js/bootstrapValidator.min.js"></script>
</head>
<body>
<%@include file="/head.jsp" %>
<h4>需要邀请码请联系管理员</h4>
<div class="container">
    <div class="row">
        <!-- form: -->
        <div class="col-lg-8 col-lg-offset-2">
            <div class="page-header">
                <h2>注册</h2>
            </div>

            <form:form id="registerForm" modelAttribute="user" method="post"
                       class="form-horizontal"
                       action="${pageContext.request.contextPath }/register">
                <div class="form-group">
                    <label class="col-lg-3 control-label">昵称</label>
                    <div class="col-lg-5">
                        <input path="nickname" type="text" class="form-control"
                               name="nickName" placeholder="请输入昵称" value="${user.nickname }"/>
                        <form:errors path="nickname"></form:errors>
                        <span id="checknickname">${nickNameError }</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label">用户名</label>
                    <div class="col-lg-5">
                        <input path="username" type="text" class="form-control"
                               name="userName" placeholder="请输入用户名" value="${user.username }"/>
                        <form:errors path="username"></form:errors>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label">密码</label>
                    <div class="col-lg-5">
                        <input path="password" type="password" class="form-control"
                               name="passWord" placeholder="请输入密码" value="${user.password }"/>
                        <form:errors path="password"></form:errors>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label">确认密码</label>
                    <div class="col-lg-5">
                        <input type="password" class="form-control"
                               name="confirmPassword" placeholder="请输入密码"/>
                        <form:errors path="confirmPassword"></form:errors>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label">电子邮箱</label>
                    <div class="col-lg-5">
                        <input path="email" type="text" class="form-control" name="email"
                               placeholder="请输入电子邮箱" value="${user.email }"/>
                        <form:errors path="email"></form:errors>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label">邀请码</label>
                    <div class="col-lg-5">
                        <input type="text" class="form-control" name="inviteCode"
                               placeholder="请输入邀请码"/>
                        <form:errors path="inviteCode"></form:errors>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label" id="captchaOperation"></label>
                    <div class="col-lg-2">
                        <input type="text" class="form-control" name="captcha"
                               placeholder="请输入结果"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-lg-9 col-lg-offset-3">
                        <button type="submit" class="btn btn-primary" name="signup"
                                value="Sign up">提交
                        </button>
                        <button type="button" class="btn btn-info" id="validateBtn">检查</button>
                        <button type="button" class="btn btn-info" id="resetBtn">重置</button>
                    </div>
                </div>
            </form:form>
        </div>
        <!-- :form -->
    </div>
</div>

<script type="text/javascript">
    $(document)
        .ready(
            function () {
                // Generate a simple captcha
                function randomNumber(min, max) {
                    return Math.floor(Math.random()
                        * (max - min + 1) + min);
                }
                ;
                $('#captchaOperation').html(
                    [randomNumber(1, 100), '+',
                        randomNumber(1, 200), '=']
                        .join(' '));

                $('#registerForm')
                    .bootstrapValidator(
                        {
                            //        live: 'disabled',
                            message: 'This value is not valid',
                            feedbackIcons: {
                                valid: 'glyphicon glyphicon-ok',
                                invalid: 'glyphicon glyphicon-remove',
                                validating: 'glyphicon glyphicon-refresh'
                            },
                            fields: {
                                nickName: {
                                    validators: {
                                        notEmpty: {
                                            message: '昵称不为空'
                                        }
                                    }
                                },
                                userName: {
                                    message: '用户名不合法',
                                    validators: {
                                        notEmpty: {
                                            message: '用户名不为空'
                                        },
                                        stringLength: {
                                            min: 6,
                                            max: 30,
                                            message: '用户名长度为 6到30'
                                        },
                                        regexp: {
                                            regexp: /^[a-zA-Z0-9_\.]+$/,
                                            message: '用户名只能由字母数字小数点和下划线组成'
                                        },
                                        remote: {
                                            url: '${pageContext.request.contextPath }/checkUserName',
                                            message: '用户名已被注册'
                                        },
                                        different: {
                                            field: 'passWord',
                                            message: '用户名和密码不可相同'
                                        }
                                    }
                                },
                                email: {
                                    validators: {
                                        notEmpty: {
                                            message: '请输入邮箱'
                                        },
                                        emailAddress: {
                                            message: '邮箱地址不合法'
                                        }
                                    }
                                },
                                passWord: {
                                    validators: {
                                        notEmpty: {
                                            message: '密码不为空'
                                        },
                                        identical: {
                                            field: 'confirmPassword',
                                            message: '两次密码不一致'
                                        },
                                        different: {
                                            field: 'userName',
                                            message: '用户名和密码不可相同'
                                        }
                                    }
                                },
                                confirmPassword: {
                                    validators: {
                                        notEmpty: {
                                            message: '密码不为空'
                                        },
                                        identical: {
                                            field: 'passWord',
                                            message: '两次密码不一致'
                                        },
                                        different: {
                                            field: 'userName',
                                            message: '用户名和密码不可相同'
                                        }
                                    }
                                },
                                inviteCode: {
                                    validators: {
                                        remote: {
                                            url: '${pageContext.request.contextPath }/checkInviteCode',
                                            message: '邀请码错误'
                                        },
                                        notEmpty: {
                                            message: '请输入邀请码'
                                        }
                                    }
                                },
                                captcha: {
                                    validators: {
                                        callback: {
                                            message: '答案错误',
                                            callback: function (
                                                value,
                                                validator) {
                                                var items = $(
                                                    '#captchaOperation')
                                                    .html()
                                                    .split(
                                                        ' '), sum = parseInt(items[0])
                                                    + parseInt(items[2]);
                                                return value == sum;
                                            }
                                        }
                                    }
                                }
                            }
                        });

                // Validate the form manually
                $('#validateBtn').click(
                    function () {
                        $('#registerForm').bootstrapValidator(
                            'validate');
                    });

                $('#resetBtn').click(
                    function () {
                        $('#registerForm').data(
                            'bootstrapValidator')
                            .resetForm(true);
                    });
            });
</script>
</body>
</html>