<template>
    <div>
            <!-- 面包屑导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>报修记录</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card>
        <!-- 数据表格 -->
    <el-table
    :data="tableData"
    style="width: 100%">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="报修时间">
            <span>{{ props.row.date }}</span>
          </el-form-item>
          <el-form-item label="家庭住址">
            <span>{{ props.row.location }}</span>
          </el-form-item>
          <el-form-item label="联系电话">
            <span>{{ props.row.phone }}</span>
          </el-form-item>
          <el-form-item label="问题类型">
            <span>{{ props.row.type }}</span>
          </el-form-item>
          <el-form-item label="问题描述">
            <span>{{ props.row.desc }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column
      label="报修时间"
      prop="date">
    </el-table-column>
    <el-table-column
      label="问题图片"
      >
      <template slot-scope="props">
        <div v-for="(image, index) in props.row.pics" :key="index"
        style="width:130px;float:left">
          <el-image :src="image" class="image-item">
          </el-image>
        </div>
      </template>
    </el-table-column>
    <el-table-column
      label="问题描述"
      prop="desc">
    </el-table-column>
  </el-table>
    </el-card>
    </div>
</template>
<script>
export default {
    data() {
        return{
            tableData:[],
        }
    },
    created(){
        this.getRecords()
    },
    methods:{
        //获取报修记录
        async getRecords(){
            const{data:res} = await this.$http.get('getRecords')
            if(res.code!=1) return this.$message.error("获取报修记录失败")
            this.tableData = res.data
            this.splitPics()
        },
        //分割图片路径
        splitPics(){
            for(let i =0;i<this.tableData.length;i++){
            var m = this.tableData[i].pics.split(",")
            this.tableData[i].pics = m
            }
        }
    }
}
</script>

<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
.image-item {
  max-width: 100px;
  max-height: 100px;
}
</style>