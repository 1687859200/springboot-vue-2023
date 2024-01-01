<template>
  <div>
<!--功能区域-->
    <div style="margin: 20px">
      <el-button type="primary" plain @click="dialogFormVisible = true;form = {}">新增</el-button>
      <el-button type="primary" plain>导入</el-button>
      <el-button type="primary" plain>导出</el-button>
    </div>
    <div>
      <el-dialog v-model="dialogFormVisible" style="width: 30vh">
        <el-form :model="form">
          <el-form-item label="Name" label-width="15%">
            <el-input clearable v-model="form.name" autocomplete="off" />
          </el-form-item>
          <el-form-item label="Age" label-width="15%">
            <el-input clearable v-model="form.age" autocomplete="off" />
          </el-form-item>
          <el-form-item label="Phone" label-width="15%">
            <el-input clearable v-model="form.phone" autocomplete="off" />
          </el-form-item>
          <el-form-item label="Sex" label-width="15%">
            <el-select v-model="form.sex" placeholder="Please select">
              <el-option label="男" value="男" />
              <el-option label="女" value="女" />
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submit">Confirm</el-button>
      </span>
        </template>
      </el-dialog>
    </div>
<!--搜索区域-->
    <div style="display:flex;margin: 20px;width: 40vh">
      <el-input clearable v-model="params.name" style="width: 300px" placeholder="搜索姓名" />
      <el-input clearable v-model="params.phone" style="width: 300px" placeholder="搜索电话" />
      <el-button type="primary" style="margin-left: 10px" @click="findBySearch">查询</el-button>
    </div>
<!--图表区域-->
    <div style="margin: 20px;width: 80vh">
      <el-table :data="tableData" stripe>
        <el-table-column fixed prop="name" label="Name" width="120" />
        <el-table-column prop="sex" label="Sex" width="120" />
        <el-table-column prop="age" label="Age" width="120" />
        <el-table-column prop="phone" label="Phone" width="600" />
        <el-table-column fixed="right" label="Operations" width="120">
          <template v-slot="scope">
            <el-popconfirm title="Are you sure to delete this?" @confirm="del(scope.row.id)">
              <template #reference>
                <el-button link type="primary" size="small">Detail</el-button>
              </template>
            </el-popconfirm>
            <el-button link type="primary" size="small" @click="edit(scope.row)">Edit</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          v-model:current-page="params.currentPage"
          v-model:page-size="params.pageSize"
          :page-sizes="[1, 2, 5, 10]"
          small
          background
          layout="total, sizes, prev, pager, next"
          :total="total"
          class="mt-4"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          style="margin-top: 10px"
      />
    </div>
  </div>
</template>

<script>
import request from "@/utils/request.js";
import { ElMessage } from 'element-plus'
export default {
  data () {
    return {
      params: {
        name: '',
        phone: '',
        currentPage: 1,
        pageSize: 10
      },
      total: 0,
      tableData : [],
      dialogFormVisible: false,
      form: {}
    }
  },
  created() {
    this.load();
  },
  methods : {
    load () {
      request.get('/user/getAll').then(res => {
        if(res.code === '0'){
          this.tableData = res.data;
        }else{
          ElMessage({
            showClose: true,
            message: 'fail to data',
            type: 'error',
          })
        }
      })
    },
    edit (obj) {
      this.form = obj;
      this.dialogFormVisible = true;
    },
    submit () {
      request.post('/user/add', this.form).then(res => {
        if(res.code === '0'){
          ElMessage({
            showClose: true,
            message: 'success add',
            type: 'success',
          })
          this.dialogFormVisible = false;
          this.load();
        }else{
          ElMessage({
            showClose: true,
            message: res.message,
            type: 'error',
          })
        }
      })
    },
    findBySearch () {
      request.get("/user/search", {
        params: this.params
      }).then( res => {
        if(res.code === '0'){
          this.tableData = res.data.list;
          this.total = res.data.total;
        }else{
          ElMessage({
            showClose: true,
            message: res.message,
            type: 'error',
          })
        }
      })
    },
    del (id) {
      request.delete('/user/del/' + id).then(res => {
        if(res.code === '0'){
          this.load();
        }else{
          ElMessage({
            showClose: true,
            message: res.message,
            type: 'error',
          })
        }
      })
    },
    handleCurrentChange (currentPage) {
      this.params.currentPage = currentPage;
      this.findBySearch();
    },
    handleSizeChange (pageSize) {
      this.params.pageSize = pageSize;
      this.findBySearch();
    },
  }
}
</script>