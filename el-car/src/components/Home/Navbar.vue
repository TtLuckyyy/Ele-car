<template>
  <div class="navbar">
    <div class="navbar-content">
      <span class="welcome-text">🌟智享电力，驶向未来🌟</span>
      <div class="nav-buttons">
        <button class="nav-btn">指南</button>
        <button class="nav-btn">资源</button>
        <div class="search-container">
          <input
            type="text"
            class="search-input"
            placeholder="搜索..."
          />
          <button class="search-button">搜索</button>
        </div>
      </div>
      <img class="logo" src="@/assets/logo.jpg" alt="Logo" />
    </div>
  </div>
</template>

<script>
export default {
  name: "NavBar",
};
</script>

<style scoped>
.navbar {
  position: fixed;
  top: 0;
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(90deg, rgba(206, 210, 210, 0.8), rgba(244, 245, 246, 0.8)); /* 水平渐变色背景 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加轻微阴影 */
  z-index: 1000; /* 确保导航栏在页面最上层 */
  backdrop-filter: blur(8px); /* 模糊背景效果 */
  transition: background 0.5s ease; /* 平滑过渡 */
}

.navbar-content {
  width: 95%; /* 控制内容宽度 */
  max-width: 2100px; /* 最大宽度 */
  display: flex;
  justify-content: space-between; /* 在两侧均匀分配空间 */
  align-items: center;
}

.welcome-text {
  margin-left: 300px;
  font-size: 23px;
  font-weight: bold;
  color: #333; /* 文本颜色 */
  text-align: center;
  flex-grow: 1; /* 使文字占据中间位置 */
}

.nav-buttons {
  display: flex; /* 设置为flex布局 */
  align-items: center; /* 垂直居中对齐 */
}

.nav-btn {
  padding: 5px 10px;
  border: none;
  background-color: #55727ecc; /* 按钮背景颜色 */
  color: white;
  border-radius: 4px;
  margin-left: 5px; /* 按钮之间的间隔 */
  cursor: pointer;
}

.search-container {
  display: flex;
  align-items: center; /* 垂直居中 */
  margin-left: 10px; /* 为搜索框留出一些空间 */
}

.search-input {
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-right: 5px;
  width: 150px; /* 设置搜索框宽度 */
}

.search-button {
  padding: 5px 10px;
  border: none;
  background-color: #55727ecc; /* 按钮背景颜色 */
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

.logo {
  width: 40px;
  height: 40px;
  margin-left: 30px; /* 将 logo 和按钮之间留出空间 */
}
</style>
