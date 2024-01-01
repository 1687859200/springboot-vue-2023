<template>
  <div class="login-container">
    <div class="login-header">
      <h2>欢迎登录</h2>
    </div>
    <input v-model="admin.name" type="text" class="login-input" placeholder="用户名">
    <input v-model="admin.password" type="password" class="login-input" placeholder="密码">
    <button class="login-button" @click="login">登录</button>
    <div class="login-footer">
      <p>还没有账号？<a href="#">注册一个</a></p>
    </div>
  </div>
  <div class="background"></div>
</template>

<script>
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
export default {
  data () {
    return {
      admin: {
        name: '',
        password: ''
      }
    }
  },
  methods: {
    login () {
      if(this.admin.name !== "" && this.admin.password !== ""){
        request.post('/user/login', this.admin).then(res => {
          if(res.code === '0'){
            localStorage.setItem("user", JSON.stringify(res.data));
            this.$router.push("/")
          }else{
            ElMessage({
              showClose: true,
              message: res.message,
              type: 'error',
            })
          }
        })
      }else{
        ElMessage({
          showClose: true,
          message: "请输入用户名和密码",
          type: 'error',
        })
      }
    }
  }
}
</script>

<style scoped>
body {
  margin: 0;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  overflow: hidden;
  background: #1e1e1e;
}

.login-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 300px;
  padding: 20px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  overflow: hidden;
  animation: fadeIn 1s ease;
}


.login-header {
  text-align: center;
  margin-bottom: 20px;
  color: #3498db;
}

.login-input {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  outline: none;
  transition: border-color 0.3s ease;
}

.login-input:focus {
  border-color: #3498db;
}

.login-button {
  width: 100%;
  padding: 10px;
  color: #fff;
  background: linear-gradient(45deg, #3498db, #8e44ad);
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s ease;
}

.login-button:hover {
  background: linear-gradient(45deg, #2980b9, #9b59b6);
}

.login-footer {
  margin-top: 20px;
  text-align: center;
}

.login-footer a {
  color: #3498db;
  text-decoration: none;
}

.login-footer a:hover {
  text-decoration: underline;
}

.background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(45deg, #3498db, #8e44ad, #3498db, #8e44ad);
  opacity: 0.5;
  z-index: -1;
  animation: backgroundAnimation 10s infinite linear;
}

@keyframes backgroundAnimation {
  0% {
    background-position: 0% 0%;
  }
  100% {
    background-position: 100% 100%;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>
