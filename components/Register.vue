<template>
<div class="container">
<div class="registerBox">
<!--注册表单区域-->
<el-form  ref="regFormRef" label-width="0px" class="register_form" :model="registerForm" :rules="registerRules">
  <!-- 标题 -->
  <h2 class="title">注册</h2>
    <!--用户名-->
  <el-form-item prop="username" >
    <el-input   prefix-icon="el-icon-user-solid"  placeholder="用户名" v-model="registerForm.username"></el-input>
  </el-form-item>
    <!--邮箱-->
  <el-form-item prop="email" >
    <el-input  prefix-icon="el-icon-message"   placeholder="邮箱" v-model="registerForm.email"></el-input>
  </el-form-item>
  <!--密码-->
  <el-form-item prop="password" >
    <el-input  prefix-icon="el-icon-lock"  show-password placeholder="密码" v-model="registerForm.password"></el-input>
  </el-form-item>
   <!--确认密码-->
  <el-form-item prop="check_password" >
    <el-input  prefix-icon="el-icon-lock"  show-password placeholder="确认密码" v-model="registerForm.check_password"></el-input>
  </el-form-item>
  <!--按钮-->
  <el-form-item class="btns">
      <el-button type="primary" @click="Register">注册</el-button>
      <el-button @click="goLogin">取消</el-button>
  </el-form-item>
</el-form>
</div>
</div>

</template>

<script>
export default{
    data (){
        //邮箱验证正则表达式
      var checkEmail = (rule, value, cb) => {
      const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
      if (regEmail.test(value)) {
        // 合法的邮箱
        return cb();
        }
        cb(new Error(""));
        };
        //验证两次密码是否相同
        var checkPass = (rule,value,cb) => {
            if (value === '') {
          cb(new Error(''));
        } else if (value !== this.registerForm.password) {
          cb(new Error(''));
        } else {
          cb();
        }
        };

        return{
            registerForm:{},
            registerRules:{
            // 验证用户名是否合法
            username: [{ required: true, message: '请输入登录名称', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在三到十个字符之间', trigger: 'blur' }],
            // 验证密码是否合法
            password: [{ required: true, message: '请输入登录密码', trigger: 'blur' },
            { min: 6, max: 15, message: '长度在六到十五个字符之间', trigger: 'blur' }],
            //验证邮箱是否合法
            email: [
            {required: true, message: "请输入邮箱地址",trigger: "blur"},
            {validator: checkEmail,message: "请输入正确的邮箱地址",trigger: "blur"}],
            check_password:[{required: true,message:"请再次输入密码",trigger:"blur"},
            {validator:checkPass,message:"两次输入密码不同",trigger:"blur"}]
            }
        }
    },
    methods:{
        //点击取消，返回登陆页面
        goLogin(){
            this.$router.push('/login')
        },
        //点击注册，完成注册
        Register(){
          this.$refs.regFormRef.validate(async valid =>{
            if(!valid) return false
            const{data:res} = await this.$http.post('register',this.registerForm)
            if(res.code!==1) return this.$message.error("用户名已存在!")
            this.$message.success("注册成功!")
            this.$router.push('/login')
          })
        }
    }
}
</script>

<style lang="less" scoped>
.container{
    background-image: linear-gradient(120deg, #e0c3fc 0%, #8ec5fc 100%);
    height: 100%;
}
.registerBox{
width: 450px;
height: 440px;
background-color: #fff;
border-radius: 3px;
position: absolute;
left: 50%;
top: 50%;
transform: translate(-50%,-50%);
}
.register_form{
    position: absolute;
    top: 20px;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;

}
.btns{
    display: flex;
    justify-content: flex-end;
}
.title{

}
</style>