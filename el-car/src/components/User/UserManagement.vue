<template>
  <div class="user-management">
    <h2>用户信息管理</h2>

    <!-- 搜索框和筛选条件 -->
    <div class="filter-container">
      <!-- 用户身份筛选 -->
      <el-select v-model="searchIdentity" placeholder="选择身份" style="width: 150px; margin-right: 20px;">
        <el-option label="用户" value="1"></el-option>
        <el-option label="管理员" value="2"></el-option>
        <el-option label="运营商" value="3"></el-option>
      </el-select>

      <!-- 用户状态筛选 -->
      <el-select v-model="searchStatus" placeholder="选择状态" style="width: 150px;">
        <el-option label="激活" value="active"></el-option>
        <el-option label="禁用" value="inactive"></el-option>
      </el-select>
    </div>

    <!-- 用户列表表格 -->
    <el-table
      v-loading="loading"
      :data="currentUserList"
      border
      stripe
      class="custom-table"
      style="width: 100%;"
    >
      <el-table-column prop="username" label="用户名" align="center" min-width="15%" />
      <el-table-column prop="email" label="邮箱" align="center" min-width="25%" />
      <el-table-column prop="phone" label="联系电话" align="center" min-width="20%" />
      <el-table-column prop="status" label="状态" align="center" min-width="10%">
        <template #default="scope">
          <el-tag :type="scope.row.status === 'active' ? 'success' : 'danger'" effect="dark">
            {{ scope.row.status === 'active' ? '激活' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="30%">
        <template #default="scope">
          <div class="action-buttons">
            <el-button size="mini" type="primary" @click="editUser(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteUser(scope.row)" :loading="scope.row.loading">
              删除
            </el-button>
            <el-button
              size="mini"
              :type="scope.row.status === 'active' ? 'warning' : 'success'"
              @click="toggleStatus(scope.row)"
              :loading="scope.row.loading"
            >
              {{ scope.row.status === 'active' ? '禁用' : '激活' }}
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <div class="pagination-container" style="margin-top: 20px; text-align: center;">
      <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="filteredUserList.length"
        @current-change="handlePageChange"
        layout="total, prev, pager, next, jumper"
      />
    </div>

    <div class="add-user-button">
      <el-button type="primary" @click="AddUser">添加用户</el-button>
    </div>

    <UserProfileDialog 
      :dialogVisible="dialogVisible" 
      :currentUser="userWithoutLoading" 
      @userEditComplete="fetchAllUsers"
      @closedialogvisible="closedialogvisible" 
    />
  </div>
</template>


<script>
import axios from 'axios';
import UserProfileDialog from '../User/UserProfileDialog.vue';

export default {
  components: {
    UserProfileDialog,
  },
  inject: ['user'],
  data() {
    return {
      searchKeyword: '',    // 搜索框内容
      searchIdentity: '',   // 用户身份筛选条件
      searchStatus: '',     // 用户状态筛选条件
      userWithoutLoading: null,
      dialogVisible: false,
      userList: [],         // 存放用户数据
      loading: false,       // 全局加载状态
      currentPage: 1,       // 当前页
      pageSize: 10,         // 每页显示的记录数
    };
  },
  created() {
    this.fetchAllUsers();  // 在组件创建时获取数据
  },
  computed: {
    // 计算属性，用于动态过滤用户列表
    filteredUserList() {
      return this.userList.filter((user) => {
        const matchesIdentity = !this.searchIdentity || user.identity.toString() === this.searchIdentity;
        const matchesStatus = !this.searchStatus || user.status === this.searchStatus;

        return matchesIdentity && matchesStatus;
      });
    },
    // 当前页显示的用户列表
    currentUserList() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = this.currentPage * this.pageSize;
      return this.filteredUserList.slice(start, end);
    },
  },
  methods: {
    // 获取所有用户信息
    async fetchAllUsers() {
      this.loading = true;
      try {
        const response = await axios.get('/home/user/getAllUsers');
        this.userList = response.data.map(user => ({
          ...user,
          loading: false, // 为每行新增 loading 字段
        }));
      } catch (error) {
        console.error('获取用户数据失败:', error);
        this.$message.error('获取用户数据失败，请稍后重试');
      } finally {
        this.loading = false;
      }
    },
    // 添加新用户
    AddUser() {
      this.userWithoutLoading = { id: 'null', identity: 'null', gender: 'null', phone: 'null', username: 'null', password: 'null', email: 'null', status: 'active' };
      this.dialogVisible = true;  // 打开用户对话框
    },
    // 编辑用户信息
    editUser(user) {
      this.userWithoutLoading = { ...user };
      delete this.userWithoutLoading.loading; // 删除 loading 属性
      this.dialogVisible = true;  // 打开用户对话框
    },
    // 删除用户信息
    deleteUser(user) {
      this.loading = true;  // 开始加载，防止多次点击
      if (user.id === this.user.id) {
        this.$message.error('不可删除自己，请重试');
        this.loading = false;  // 完成操作，关闭加载状态
      } else if (user.identity === 2) {
        this.$message.error('不可删除其他管理员，请重试');
        this.loading = false;  // 完成操作，关闭加载状态
      } else {
        axios
          .delete(`/home/user/deleteUser/${user.id}`)  // 假设你的 API 使用 DELETE 方法并传递用户 ID
          .then(response => {
            if (response.data.status === 'success') {
              this.userList = this.userList.filter(u => u.id !== user.id);  // 从本地列表中删除该用户
              this.$message.success('用户已删除');
            } else {
              console.error('删除用户时出错:', response.data.message);
              this.$message.error('删除失败，请稍后重试');
            }
          })
          .catch(error => {
            console.error('删除用户时出错:', error);
            this.$message.error('删除失败，请稍后重试');
          })
          .finally(() => {
            this.loading = false;  // 完成操作，关闭加载状态
          });
      }
    },
    // 更改用户状态信息
    async toggleStatus(user) {
      this.loading = true;  // 设置行级 loading
      if (user.id === this.user.id) {
        this.$message.error('不可修改当前用户状态，请重试');
        this.loading = false;  // 完成操作，关闭加载状态
      } else if (user.identity === 2) {
        this.$message.error('不可修改其他管理员状态，请重试');
        this.loading = false;  // 完成操作，关闭加载状态
      } 
      else{
          const newStatus = user.status === 'active' ? 'inactive' : 'active';  // 新的用户状态
          try {
            const response = await axios.post('/home/user/updateStatus', {
              id: user.id,          // 用户ID
              status: newStatus,    // 新状态
            });

            if (response.data.status === 'success') {
              user.status = newStatus;  // 更新本地状态
              this.$message.success(`用户状态已${newStatus === 'active' ? '激活' : '禁用'}`);
            } else {
              this.$message.error('状态更新失败，请稍后重试');
            }
          } catch (error) {
            console.error('更改用户状态时出错:', error);
            this.$message.error('更改状态失败，请稍后重试');
          } finally {
            await new Promise((resolve) => setTimeout(resolve, 300));  // 最后再延迟0.5秒
            user.loading = false;  // 操作完成，关闭行级 loading
          }
       }
    },

    // 关闭用户信息对话框
    closedialogvisible(visible) {
      this.dialogVisible = visible;
    },
    // 处理分页切换
    handlePageChange(page) {
      this.currentPage = page;
    },

  },
};
</script>

<style scoped>
.user-management {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 20px;
  color: #333;
  font-weight: 600;
}

.custom-table {
  width: 100%;
}

.el-table__header-wrapper th {
  font-weight: 500;
  color: #333;
  background-color: #f5f7fa;
  text-align: center;
  padding: 8px 4px;
}

.el-table th,
.el-table td {
  padding: 8px 4px;
  font-size: 14px;
  vertical-align: middle;
  text-align: center;
  white-space: nowrap;
}

.el-table__row:hover {
  background-color: #f0f9ff;
}

.add-user-button {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.el-button {
  margin: 0;
  text-align: center !important;
}

.el-tag {
  font-weight: 500;
  padding: 4px 8px;
}

.filter-container {
  margin-bottom: 20px;
  display: flex;
  justify-content: flex-start;
  gap: 20px;
}
.pagination-container {
  margin-top: 20px;
  text-align: center;
}

.custom-table {
  width: 100%;
}

</style>
