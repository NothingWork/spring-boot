<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>设备管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <!--搜索与添加区域-->
          <el-input placeholder="请输入内容" v-model="queryInfo" clearable>
            <el-button slot="append" icon="el-icon-search" @click="getDeviceList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDevice">添加设备</el-button>
        </el-col>
      </el-row>
      <!--设备列表区域-->
      <el-table :data="deviceList" stripe border>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="device_id" label="设备编号"></el-table-column>
        <el-table-column prop="name" label="设备名称"></el-table-column>
        <el-table-column prop="desc" label="设备描述"></el-table-column>
        <el-table-column label="状态" >
        <template slot-scope="scope">
          <el-switch v-model="scope.row.state" @change="changeState(scope.row)">
            </el-switch>
        </template>
        </el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <el-tooltip
              class="item"
              effect="dark"
              content="编辑设备"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="editDevice(scope.row)"
              ></el-button>
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="dark"
              content="删除设备"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="removeDevice(scope.row.device_id)"
              ></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
          <!--底部分页区域-->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageInfo.pagenum"
      :page-sizes="[1, 2, 5, 10]"
      :page-size="pageInfo.pagesize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    </el-card>
    <!--修改设备的对话框-->
<el-dialog
  title="修改设备信息"
  :visible.sync="editDeviceDialog"
  @close="editDialogClose"
  width="50%">
  <!--内容主题区域-->
  <el-form ref="eidtFormRef" :model="editForm" label-width="70px" :rules="editFormRules">
  <el-form-item label="id" prop="device_id">
    <el-input v-model="editForm.device_id" disabled></el-input>
  </el-form-item>
  <el-form-item label="名称"  prop="name">
    <el-input v-model="editForm.name"></el-input>
  </el-form-item>
  <el-form-item label="描述" prop="desc">
    <el-input v-model="editForm.desc"></el-input>
  </el-form-item>
  </el-form>
  <!--底部区域-->
  <span slot="footer" class="dialog-footer">
    <el-button @click="editDeviceDialog = false">取 消</el-button>
    <el-button type="primary" @click="editDeviceInfo">确 定</el-button>
  </span>
</el-dialog>
    <!--添加设备的对话框-->
<el-dialog
  title="添加设备信息"
  :visible.sync="addDeviceDialog"
  @close="addDialogClose"
  width="50%"
  >
  <!--模拟设备查找  -->
    <div v-loading="loading"
    element-loading-text="查找设备中..."></div>
  <!--内容主题区域-->
  <el-form ref="addFormRef" :model="addForm" label-width="70px" :rules="editFormRules">
  <el-form-item label="名称"  prop="name">
    <el-input v-model="addForm.name"></el-input>
  </el-form-item>
  <el-form-item label="描述" prop="desc">
    <el-input v-model="addForm.desc"></el-input>
  </el-form-item>
  </el-form>
  <!--底部区域-->
  <span slot="footer" class="dialog-footer">
    <el-button @click="addDeviceDialog = false">取 消</el-button>
    <el-button type="primary" @click="addDeviceInfo">确 定</el-button>
  </span>
</el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //分页数据，查询数据时传入
      //储存表格数据备份，用于分割
      list:[],
      pageInfo:{
        pagenum:1,//当前页数
        pagesize:5,//每页数据
      },
      total:0,//数据总数
      //用户信息
      userInfo:"",
      //设备列表数据
      deviceList: [],
      //查询内容体
      queryInfo:'',
      //编辑设备信息对话框的可见性
      editDeviceDialog: false,
      //添加设备对话框的可见性
      addDeviceDialog: false,
      //模拟设备查找
      loading: true,
      //编辑设备表单
      editForm:{
        name:'',
        desc:'',
        device_id:'',
        user_id:''//用于后端查找用，不在页面展示
      },
      //添加设备表单 
      addForm:{
        device_id:'',
        name:'',
        desc:'',
        state:1,//设备默认为开启状态
        user_id:''
      },
      //编辑设备表单的表单规则
      editFormRules:{
        name:[{required:true,message:"请输入设备名称",trigger:'blur'}],
        desc:[{required:true,message:"请输入设备描述",trigger:'blur'}]
      }
    };
  },
  created() {
      this.getUserInfo();//获取用户信息
      this.getDeviceList();//获取设备列表
  },
  methods: {
    //分页
    //每次分页参数改变后重新分割数组
    changeTable(){
      let begin = 0+this.pageInfo.pagesize*(this.pageInfo.pagenum-1)
      let end = begin+this.pageInfo.pagesize
      this.deviceList = this.list.slice(begin,end)
    },
    // 监听每页数据数量事件改变传入新的数据数量
    handleSizeChange (newsize) {
      this.pageInfo.pagesize = newsize
      this.changeTable()
    },
    //监听当前页数改变，并传入想要查看的页数
    handleCurrentChange (newpage) {
      this.pageInfo.pagenum = newpage
      this.changeTable()
    },
    //获取设备信息，填充表单
    async getDeviceList() {
      const { data: res } = await this.$http.get("queryDevices",{params:
      {
        user_id:this.userInfo.user_id,
        queryInfo:this.queryInfo
      }    
      });
      if (res.code !== 1) return this.$message.error("获取设备数据失败");
      this.deviceList = res.data;
      this.total = this.deviceList.length
      //备份数据
      this.list = res.data
      this.changeTable();//分页
    },
    //获取用户信息,用于传输用户id
    getUserInfo() {
      this.userInfo = JSON.parse(window.sessionStorage.getItem("userInfo"));
    },
    //更改设备状态
    async changeState(row){
      const { data:res } = await this.$http.post("changeState?device_id="+row.device_id+"&state="+row.state);
      if(res.code !== 1) return this.$message.error("更新失败")
      this.$message.success("更改设备状态成功!")
      // console.log(row.state+" "+row.device_id)
    },
    //点击编辑按钮，打开对应设备的修改对话框
    editDevice(row){
      this.editDeviceDialog = true
      this.editForm.device_id = row.device_id
      this.editForm.name = row.name
      this.editForm.desc = row.desc
      this.editForm.user_id = row.user_id
    },
    //点击添加设备按钮，打开添加设备对话框
    async addDevice(){
      this.addDeviceDialog = true
      //发送设备查找请求
      const{data:res} = await this.$http.post('searchDevice')
      if(res.code!==1) return this.$message.error("未找到设备！")
      this.addForm.name = res.data
      this.$message.success("已找到设备")
      this.loading = false
    },
    // 编辑设备信息对话框关闭的重置操作
    editDialogClose () {
      this.$refs.eidtFormRef.resetFields()
    },
    // 添加设备对话框关闭的重置操作
    addDialogClose (){
      this.$refs.addFormRef.resetFields()
      this.loading = true
    },
    //点击确定按钮，提交更改表单
    editDeviceInfo(){
      console.log()
      this.$refs.eidtFormRef.validate(async valid =>{
        if(!valid) return false
        const{data:res} = await this.$http.post('updateDevice',this.editForm)
        if(res.code!==1) return this.$message.error("更改失败！已存在同样描述的设备")
        this.$message.success("更改成功！")
        this.editDeviceDialog = false //关闭对话框
        this.getDeviceList()//更新设备列表
      })
    },
    //点击确定按钮，添加设备
    addDeviceInfo(){
       this.addForm.user_id = this.userInfo.user_id//用于向后台提交
       this.$refs.addFormRef.validate(async valid =>{
         if(!valid) return false
         const{data:res} = await this.$http.post('addDevice',this.addForm)
         if(res.code!==1) return this.$message.error("添加失败！已存在同样描述的设备")
         this.$message.success("添加成功！")
         this.addDeviceDialog = false//关闭对话框
         this.getDeviceList()//更新设备
         this.loading = true //加载条重置
       })
    },
    //点击删除按钮，删除设备
    async removeDevice (device_id) {
      //删除前的提示
      const confirmResult = await this.$confirm(
        '此操作将永久删除该用户，是否继续？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)
      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消删除，则返回值为字符串 cancel
      if (confirmResult !== 'confirm') return this.$message.info('已取消删除')
      // 删除用户操作
      const { data: res } = await this.$http.post('removeDevice?device_id='+device_id)
      if (res.code!== 1) return this.$message.error('删除设备失败!')
      this.getDeviceList()
      this.$message.success('已删除此设备!')
    }
  }
};
</script>

<style lang="less" scoped>
</style>