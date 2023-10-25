<template>
    <div class="login_container">
<div class="login_box">
    <!--头像区域-->
    <div class="avatar_box">
        <img src="../assets/logo.png" alt="">
    </div>
    <!--登录表单区域-->
<el-form ref="loginFormRef" :rules="loginFormRules" :model="loginForm" label-width="0px" class="login_form">
    <!--用户名-->
  <el-form-item prop="username" >
    <el-input   prefix-icon="el-icon-user-solid" v-model="loginForm.username" placeholder="用户名"></el-input>
  </el-form-item>
  <!--密码-->
  <el-form-item prop="password" >
    <el-input  prefix-icon="el-icon-lock" v-model="loginForm.password" show-password placeholder="密码"></el-input>
  </el-form-item>
  <!--按钮-->
  <el-form-item class="btns">
      <span class="goReg" @click="goRegister">去注册</span>
    <el-button type="primary" @click="login">登录</el-button>
    <el-button @click="resetLoginForm">重置</el-button>
  </el-form-item>
</el-form>
</div>
    </div>
</template>

<script>
export default {
     data () {
    return {
        loginForm:{   
        },
      // 这是表单验证规则对象
      loginFormRules: {
      // 验证用户名是否合法
        username: [{ required: true, message: '请输入登录名称', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在三到十个字符之间', trigger: 'blur' }],
        // 验证密码是否合法
        password: [{ required: true, message: '请输入登录密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在六到十五个字符之间', trigger: 'blur' }]
      }
    }
  },
    methods: {
    // 点击重置按钮，重置登录表单
    resetLoginForm () {
      this.$refs.loginFormRef.resetFields()
    },
    //点击去注册，跳转注册页面
    goRegister () {
      this.$router.push('/register')
    },
    //点击登录按钮，进行登录
    login(){
      this.$refs.loginFormRef.validate(async valid=>{
        if(!valid) return false
        const{data:res} = await this.$http.post('login',this.loginForm) //发送登录请求
        if(res.code!==1) return  this.$message.error("用户名或密码错误!") //校验状态码，判断是否登录成功
        this.$message.success('登录成功!')
        window.sessionStorage.setItem('token', res.data.token)//保存token
        window.sessionStorage.setItem("userInfo",JSON.stringify(res.data))//保存登入的用户的x信息)
        this.$router.push('/home')
      })
    }
  }
}
</script> 

<style lang="less" scoped>
.login_container{
    background-image: linear-gradient(120deg, #e0c3fc 0%, #8ec5fc 100%);
    height:100%;
}
.login_box{
width: 450px;
height: 300px;
background-color: #fff;
border-radius: 3px;
position: absolute;
left: 50%;
top: 50%;
transform: translate(-50%,-50%);
}

.avatar_box{
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left:50%;
    transform: translate(-50%,-50%);
    background-color: #fff;
    img{
        width: 100%;
        height: 100%;
        border-radius: 50%;
        background-color: #eee;
    }
}
.login_form{
    position: absolute;
    bottom: 0;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;

}
.btns{
    display: flex;
    justify-content: flex-end;
}
.goReg{
  position: relative;
 left: -210px;
 color:#62abf5;
 cursor: pointer;
}
</style>