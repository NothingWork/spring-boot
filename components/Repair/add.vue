<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>新增报修</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片试图 -->
    <el-card>
        <el-alert
    title="个人信息的收集仅为帮助后续上门维修工作"
    type="info"
    center
    :closable="false"
    show-icon
    style="margin-bottom:10px">
  </el-alert>
      <el-form ref="form" :model="form" label-width="80px" :rules="formRules">
        <el-form-item label="家庭住址" prop="location">
          <el-cascader
            v-model="form.region"
            placeholder="请选择您所在区域"
            :options="options"
          >
          </el-cascader>
          <el-input
            v-model="form.location"
            style="width: 400px"
            placeholder="输入详细地址"
          ></el-input>
        </el-form-item>
        <el-form-item label="联系方式" style="width: 500px" placeholder="请输入联系方式" prop="phone">
          <el-input v-model="form.phone"
          :maxlength="11"
          :show-word-limit="true"></el-input>
        </el-form-item>
        <el-form-item label="问题类型" prop="type"
          >
          <el-checkbox-group v-model="form.type">
            <el-checkbox label="设备启动问题" name="type"></el-checkbox>
            <el-checkbox label="网络设置问题" name="type"></el-checkbox>
            <el-checkbox label="软件问题" name="type"></el-checkbox>
            <el-checkbox label="录像问题" name="type"></el-checkbox>
            <el-checkbox label="云服务问题" name="type"></el-checkbox>
            <el-checkbox label="其他问题" name="type"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="问题描述" prop="desc">
          <el-input type="textarea" v-model="form.desc" style="width:50%" placeholder="请尽可能详细的描述问题"></el-input>
        </el-form-item>
        <el-form-item label="添加图片">
          <el-upload
            action="#"
            list-type="picture-card"
            :auto-upload="false"
            :file-list="fileList"
            :on-remove="fileRemove"
            :on-change="fileChange"
          >
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{ file }">
              <img
                class="el-upload-list__item-thumbnail"
                :src="file.url"
                alt=""
              />
              <!-- 图片预览 -->
              <span class="el-upload-list__item-actions">
                <span
                  class="el-upload-list__item-preview"
                  @click="handlePictureCardPreview(file)"
                >
                  <i class="el-icon-zoom-in"></i>
                </span>
                <!-- 图片下载 -->
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleDownload(file)"
                >
                  <i class="el-icon-download"></i>
                </span>
                <!-- 图片移除 -->
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleRemove(file)"
                >
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="" />
          </el-dialog>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
//包含中国省市，用于级联选择器
import { regionData, codeToText } from "element-china-area-data";
//引入js下载文件功能，用于下载文件
import { saveAs } from "file-saver";
export default {
  data() {
    return {
      form: {
        region: "",
        location: "",
        phone: "",
        date1: "",
        date2: "",
        type: [],
        desc: "",
      },
      //表单验证对象
      formRules:{
        location:{ required: true, message: '请输入详细地址', trigger: 'blur' },
        phone:{required: true,pattern: /^\d{11}$/, message: '请输入正确的联系方式', trigger: 'blur'},
        type:{required: true, message:'请选择问题类型',trigger:'blur'},
        desc:{required:true,message:'请填写问题描述',trigger:'blur'}
      },
      options: regionData,
      //用于图片展示的图片路径
      dialogImageUrl: "",
      dialogVisible: false, //图片缩略图可见性
      disabled: false,
      //储存图片文件的数组
      fileList: [],
      //图片路径
      filePath:'',
      //储存图片路径的拼接字符串
      paths:'',
      //问题类型拼接字符串
      types:''
    };
  },
  methods: {
    //地址字符串获取
    getLocation() {
      var loc = "";
      for (let i = 0; i < this.form.region.length; i++) {
        loc += codeToText[this.form.region[i]];
      }
      return loc;
    },
    //检测文件变动获取文件
    fileChange(file, fileList) {
      this.fileList = fileList;
    },
    //检测文件删除
    fileRemove(file, fileList) {
      //遍历文件列表删除对应文件
      for (let i = 0; i < fileList.length; i++) {
        if (fileList[i] == file) {
          fileList.splice(i, 1);
        }
      }
      this.fileList = fileList;
    },
    //移除图片
    handleRemove(file) {
      this.fileRemove(file, this.fileList);
    },
    //预览图片
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    //下载图片
    async handleDownload(file) {
      let blob = await this.getBlobObject(file.url); //生成blob对象
      const img_path = this.generateRandomFileName(); //生成文件名
      saveAs(blob, img_path);
    },
    //生成随机文件名
    generateRandomFileName() {
      const timestamp = Date.now();
      const random = Math.random().toString(36).substring(2, 8);
      return `${timestamp}_${random}`;
    },
    //获取bolb对象用于下载
    async getBlobObject(blob_url) {
      try {
        let response = await fetch(blob_url);
        let blob = await response.blob();
        return blob;
      } catch (error) {
        console.error("获取 Blob 对象出错:", error);
      }
    },
    //图片上传功能
      async uploads(file,user){
        const formData = new FormData();
        formData.append('file',file.raw);//图片文件
        formData.append('filename',file.name);
        //用户id，用于创建对应的文件夹
        formData.append('user_id',user.user_id);
        const {data:res} = await this.$http.post('saveImg',formData)
        if(res.code!==1) return this.$message.error("图片上传失败")
        this.filePath = res.data
      },
      //获取日期
      getDate(){
      const date = new Date();
      const year = date.getUTCFullYear();
      const month = date.getUTCMonth()+1;
      const day = date.getDate();
      return year+"/"+month+"/"+day
      },
    //点击提交按钮，提交信息
    async submit() {
      this.$refs.form.validate(async valid=>{
        if(!valid) return false
    //获取用户id
      let user = JSON.parse(window.sessionStorage.getItem("userInfo"))
      //图片存于本地,拼接路径字符串
      for(let i =0;i<this.fileList.length;i++){
        if(i!=0) this.paths+=","
        await this.uploads(this.fileList[i],user)
        this.paths+=this.filePath
      } 
      //拼接问题类型
      for(let i =0;i<this.form.type.length;i++){
        if(i!=0) this.types+=","
        this.types+=this.form.type[i];
      }
      //编写响应体
      const requestBody = {
        user_id: user.user_id,
        location: this.getLocation()+this.form.location,
        phone: this.form.phone,
        type: this.types, //字符串数组
        desc: this.form.desc,
        pics: this.paths,//图片路径
        date:this.getDate()
      }
      console.log(requestBody)
      //发送请求
      const {data:res} = await this.$http.post('addRepair',requestBody)
      if(res.code!==1) return this.$message.error("添加失败")
      window.location.reload()
      return this.$message.success("添加成功")
      })
      
    },
  },
};
</script>

<style scoped>
</style>