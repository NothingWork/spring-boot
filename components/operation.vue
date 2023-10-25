<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>设备操作</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <div>
        <el-tabs type="border-card" style="width: 700px; float: right">
          <el-tab-pane label="硬件信息">
            <el-descriptions class="margin-top" :column="2" border>
              <el-descriptions-item>
                <template slot="label"> 设备id </template>
                {{ descs.device_id }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label"> 设备型号 </template>
                {{ descs.name }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label"> 生产日期 </template>
                {{ descs.date }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label"> 生产厂家 </template>
                {{ descs.factory }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label"> 分辨率 </template>
                {{ descs.resolution }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label"> 像素密度 </template>
                {{ descs.ppf }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label"> 压缩率 </template>
                {{ descs.qr }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label"> 帧数 </template>
                {{ descs.fps }}
              </el-descriptions-item>
            </el-descriptions>
          </el-tab-pane>
          <el-tab-pane label="操作云台">
              <!-- 旋转角度计数器 -->
            旋转角度 <br><el-input-number v-model="angle" :step="10"
            controls-position="right"
            :min="minAngle" :max="maxAngle"></el-input-number>
              <div style="width:420px;float: right">
            <el-button type="primary" icon="el-icon-caret-top" circle
            style="margin-left:45px;margin-bottom:10px"
            @click="sendOperation(up)"
            ></el-button><br>
            <el-button type="primary" icon="el-icon-caret-left" circle
            style=""
            @click="sendOperation(left)"></el-button>
            <el-button type="primary" icon="el-icon-caret-right" circle
            style="margin-left:50px"
            @click="sendOperation(right)"></el-button><br>
            <el-button type="primary" icon="el-icon-caret-bottom" circle
            style="margin-left:45px;margin-top:10px"
            @click="sendOperation(down)"></el-button>
            </div>
            <el-input type="textarea" 
            :autosize="{ minRows: 2, maxRows: 9}"
            v-model="log" readonly style="margin-top:10px;"
            ></el-input>
             <el-button type="info" icon="el-icon-delete" circle
             @click="clearLog"
             style="margin-top:10px"></el-button>
          </el-tab-pane>
        </el-tabs>
        <img src="../assets/device.jpg" alt="" />
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      descs: "",
      //四个方向
      up:'上',
      down:'下',
      left:'左',
      right:'右',
      //旋转角度
      angle:15,
      //最小角度与最大角度
      minAngle:15,
      maxAngle:145,
      //操作日志
      log:''
    };
  },
  created() {
    this.getAttributes();
  },
  methods: {
    //获取设备属性
    async getAttributes() {
      await this.$http.get("publish", {
        params: {
          topic: "getAttribute",
          msg: "get",
        },
      });
      const { data: res } = await this.$http.post("getMessage");
      if (res.code !== 1) return this.$message.error("获取设备信息失败");
      this.descs = JSON.parse(res.data);
      console.log(this.descs);
      return this.$message.success("成功获取设备信息");
    },
    //发送操作信息
    async sendOperation(dire){
        await this.$http.get("publish",{
                    params: {
          topic: "sendOperation",
          msg :JSON.stringify({"direction":dire,
          "angle":this.angle}),
        },
        })
      const { data: res } = await this.$http.post("getMessage");
      if (res.code !== 1) return this.$message.error("操作失败");
      console.log(res.data)
      const str = JSON.parse(res.data);
      console.log(str)
      this.log =this.log+"向"+str.direction+"旋转了"+str.angle+"度\n";
    },
    //清空操作日志
    clearLog(){
        this.log = ''
    }
  },
};
</script>

<style lang="less" scoped>
</style>