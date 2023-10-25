<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>个人信息</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>这是你的个人信息</span>
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="showEditCard"
          >更改个人信息</el-button
        >
      </div>
      <!--头像区域-->
      <div class="avatar_box">
        <img src="../assets/logo.png" alt="" />
      </div>
      <br />
      <!-- 个人信息区域 -->
      <div class="Info">
        <div>用户ID：{{ userInfo.user_id }}</div>
        <div>用户名：{{ userInfo.username }}</div>
        <div>用户邮箱：{{ userInfo.email }}</div>
      </div>
    </el-card>
    <!-- 两个卡片之间的分割线 -->
    <hr />
    <!-- 卡片隐藏时的背景图片 -->
    <img src="../assets/bg.png" class="bg" id="bg" />
    <!-- 修改用户信息卡片 -->
    <el-card class="edit-card" v-if="show">
      <div slot="header" class="clearfix">
        <span>在此处修改信息(id不可修改)</span>
        <el-button
          icon="el-icon-close"
          circle
          style="float: right; margin: -8px 0; border: none"
          @click="hideEditCard"
        ></el-button>
      </div>
      <!-- 提交修改信息的表单 -->
      <el-form :label-position="'top'" label-width="80px" ref="updateRef" :model="updateForm" :rules="updateRules">
        <el-form-item label="用户ID" prop="user_id">
          <el-input v-model="updateForm.user_id" readonly></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="updateForm.username"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="updateForm.email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="float: right" @click="update"
            >提交</el-button
          >
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    //邮箱验证正则表达式
    var checkEmail = (rule, value, cb) => {
      const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
      if (regEmail.test(value)) {
        // 合法的邮箱
        return cb();
      }
      cb(new Error(""));
    };
    return {
      userInfo: "",
      updateForm: {
          user_id:'',
          username:'',
          email:''
      },
      //控制修改卡片的是否显示
      show: false,
      updateRules: {
        // 验证用户名是否合法
        username: [
          { required: true, message: "请输入登录名称", trigger: "blur" },
          {
            min: 3,
            max: 10,
            message: "长度在三到十个字符之间",
            trigger: "blur",
          },
        ],
        //验证邮箱是否合法
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            validator: checkEmail,
            message: "请输入正确的邮箱地址",
            trigger: "blur",
          },
        ]
      },
    };
  },
  //页面创建时执行
  created() {
    this.getUserInfo();
  },
  methods: {
    //获取用户信息,并填充到修改表单中
    getUserInfo() {
      this.userInfo = JSON.parse(window.sessionStorage.getItem("userInfo"));
      this.updateForm.user_id=this.userInfo.user_id
      this.updateForm.username=this.userInfo.username
      this.updateForm.email=this.userInfo.email
    },
    //控制修改卡片显示
    showEditCard() {
      this.show = true;
      document.getElementById("bg").style.display = "none";
    },
    //控制修改卡片隐藏
    hideEditCard() {
      this.show = false;
      document.getElementById("bg").style.display = "flex";
    },
    //提交修改信息
    update() {
        //因为表单中包含只读数据，需要手动搭建请求体
        const requestBody = {
            id: this.userInfo.user_id,
            username:this.updateForm.username,
            email:this.updateForm.email
        }
          this.$refs.updateRef.validate(async valid =>{
          if(!valid) return false
          const{data:res} = await this.$http.post('update',requestBody)
          if(res.code!=1) return this.$message.error("修改失败!用户名已存在")
          this.$message.success("修改成功!请重新登录")
          this.$router.push('/login')
      })
    },
  },
};
</script>

<style lang="less" scoped>
.avatar_box {
  height: 130px;
  width: 130px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: #eee;
  }
}
.Info {
  div {
    margin-top: 15px;
  }
}
.bg {
  display: flex;
  position: relative;
  width: 300px;
  height: 300px;
  top: 63px;
  left: 79%;
}
</style>