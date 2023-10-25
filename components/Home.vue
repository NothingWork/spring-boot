<template>
    <el-container style="height:100%">
        <!-- 头部区域 -->
        <el-header>
            <div>
                <img src="../assets/logo.png" alt="">
                <span>智慧视频管理系统</span>
            </div>
            <el-button type="info" @click="logout">退出</el-button>
        </el-header>
        <!-- 页面区域 -->
    <el-container>
        <!-- 侧边栏 -->
        <el-aside :width="isCollapse ? '64px' : '200px'">
            <!-- 控制侧边栏是否折叠 -->
             <el-button icon="el-icon-s-operation" circle @click="toggleColapse"></el-button>
    <el-menu
      class="el-menu-vertical-demo"
      :collapse='isCollapse'
      :collapse-transition='false'
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      :router="true"
      :default-active="activePath">
       <el-menu-item :index="'/user'" @click="saveNavState('/user')">
        <i class="el-icon-postcard"></i>
        <span slot="title">个人信息</span>
      </el-menu-item>
      <el-menu-item index="/device" @click="saveNavState('/device')">
        <i class="el-icon-setting"></i>
        <span slot="title">设备管理</span>
      </el-menu-item>
      <el-menu-item index="/operation" @click="saveNavState('/operation')">
        <i class="el-icon-coordinate"></i>
        <span slot="title">设备操作</span>
      </el-menu-item>

 <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-warning"></i>
          <span>设备报修</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/add" @click="saveNavState('/add')">新增报修</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group>
          <el-menu-item index="/records" @click="saveNavState('/records')">报修记录</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

    </el-menu>
        </el-aside>
        <!-- 右侧主体内容 -->
        <el-main>
            <!-- 路由占位符 -->
            <router-view></router-view>
        </el-main>
    </el-container>
    </el-container>
</template>

<script>
export default {
    data(){
        return{
        //默认关闭折叠
        isCollapse: false,
        //被激活的地址
        activePath:' '
        }
    },
    created(){
        this.activePath = window.sessionStorage.getItem('activePath')
    },
    methods:{
    logout () {
      window.sessionStorage.clear()//清除session
      this.$router.push('/login')
    },
          // 折叠按钮
    toggleColapse () {
      this.isCollapse = !this.isCollapse
    },
    //保存链接的激活状态
    saveNavState(activePath){
    window.sessionStorage.setItem('activePath', activePath)
    this.activePath = activePath
    }
    }
}
</script>

<style lang="less" scoped>
.el-header {
    background-color: #455a6e;
    display: flex;
    justify-content: space-between;
    padding-left: 0;
    align-items: center;
    color: #fff;
    font-size: 20px;
    div{
        display: flex;
        align-items: center;
        img{
            width: 60px;
            height: 60px;
        }
        span{
            margin-left: 15px;
        }
    }
}
.el-aside{
    background-color: #545c64;
    .el-button{
        background-color: #545c64;
        border: none;
        width: 100%;
        color: white;
    }
    .el-menu{
        border-right: none;
    }
}
</style>