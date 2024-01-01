<script setup>
import { ArrowDown } from '@element-plus/icons-vue'
</script>

<template>
  <div style="height: 50px; line-height: 50px;border-bottom: 1px solid #ccc;display: flex">
    <div style="width: 200px;padding-left: 30px;font-weight: bolder;color: aqua">数据开发平台</div>
    <div style="flex: 1"></div>
    <div style="width: 100px;padding-top: 15px">
      <el-dropdown>
        <span class="el-dropdown-link">
          {{user.name}}
        <el-icon class="el-icon--right">
          <arrow-down/>
        </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>Action 1</el-dropdown-item>
            <el-dropdown-item>Action 2</el-dropdown-item>
            <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  methods: {
    logout() {
      localStorage.removeItem("user");
      this.$router.push("/login")
    }
  }
}
</script>

<style scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>