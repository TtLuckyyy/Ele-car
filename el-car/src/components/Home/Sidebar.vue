<template>
  <div class="sidebar">
    <el-card class="profile-card">
      <div class="profile" @click="openProfileDialog">
        <!-- 使用计算属性 userAvatar 来动态设置头像 -->
        <el-avatar class="avatar" :src="userAvatar" :style="{ width: '80px', height: '80px' }"/>
        <div class="profile-info">
          <span class="username">{{ username }}</span>
          <span class="identity">{{ userRole }}</span>
        </div>
      </div>
    </el-card>
    <el-divider></el-divider>
    
    <el-menu :default-active="activeMenu" class="el-menu-vertical">
      <el-menu-item
        v-for="item in filteredMenuItems"
        :key="item.id"
        @click="selectMenu(item)"
        class="menu-item"
      >
        <component :is="item.icon" class="menu-icon"></component>
        {{ item.name }}
      </el-menu-item> 
    </el-menu>

    <!-- 联系信息部分 -->
    <div class="contact-info">
      <h4>联系信息</h4>
      <div class="contact-item">
        <i class="el-icon-phone contact-icon"></i>
        <span>客服电话: 123-456-7890</span>
      </div>
      <div class="contact-item">
        <i class="el-icon-mail contact-icon"></i>
        <span>官方邮箱: Database@*.cn</span>
      </div>
      <div class="contact-item" style="margin-bottom: 10px;">
        <i class="el-icon-wechat contact-icon"></i>
        <span>企业微信: 2253223</span>
      </div>
    </div>
  </div>
</template>

<script>
import { inject, ref, computed } from 'vue';
import { UserFilled, Message, Tools, Goods, Service,HomeFilled,Promotion,InfoFilled,List,DocumentAdd,ShoppingCart,
   Odometer, SwitchFilled,Histogram,
   Shop,} from '@element-plus/icons-vue';
export default {
  setup(props, { emit }) {
    const user = inject('user');
    const username = ref(user.username);
    const identity = ref(user.identity);

    const userRole = computed(() => {
      switch (identity.value) {
        case 1:
          return '新能源车主';
        case 2:
          return '管理者';
        case 3:
          return '运营商';
        default:
          return '未知身份';
      }
    });

    // 根据身份设置头像路径
    const userAvatar = computed(() => {
      switch (identity.value) {
        case 1:
          return require('@/assets/Avatar/User.png');
        case 2:
          return require('@/assets/Avatar/trader.png');
        case 3:
          return require('@/assets/Avatar/Admin.png');
      }
    });

    const activeMenu = ref('1');
    const menuItems = ref([
      { id: 1, name: '主页', icon: HomeFilled },  
      { id: 2, name: '车辆信息登记', icon: Odometer },
      { id: 3, name: '充电桩位置查询', icon: Promotion },
      { id: 4, name: '充电桩预约使用', icon: Histogram },
      { id: 5, name: '平台公告查询', icon: InfoFilled },
      { id: 6, name: '主页', icon: HomeFilled },
      { id: 7, name: '用户信息管理', icon: UserFilled },
      { id: 8, name: '管理者信息登记', icon: DocumentAdd },
      { id: 9, name: '充电桩管理', icon: Histogram },
      { id: 10, name: '用户信息反馈', icon: List },
      { id: 11, name: '公告发布窗口', icon: InfoFilled },
      { id: 12, name: '充电桩实时监测', icon: Promotion },
      { id: 13, name: '主页', icon: HomeFilled },
      { id: 14, name: '运营商信息登记', icon: Service },
      { id: 15, name: '充电桩信息登记', icon: SwitchFilled },
      { id: 16, name: '充电桩管理', icon: Histogram },
      { id: 17, name: '用户信息反馈', icon: List },
      { id: 18, name: '充电桩实时监测', icon: Promotion },
      { id: 19, name: '平台公告查询', icon: InfoFilled },
      { id: 20, name: '订单查询', icon: ShoppingCart },
      { id: 21, name: '订单查询', icon: ShoppingCart },
      { id: 22, name: '订单查询', icon: ShoppingCart },
    ]);

    const filteredMenuItems = computed(() => {
      if (identity.value === 1) {
        return menuItems.value.slice(0, 5).concat(menuItems.value.slice(19, 20));
      } else if (identity.value === 2) {
        return menuItems.value.slice(5, 12).concat(menuItems.value.slice(20, 21));
      } else if (identity.value === 3) {
        return menuItems.value.slice(12, 19).concat(menuItems.value.slice(21, 22));
      }
      return [];
    });

    const openProfileDialog = () => {
      emit('openProfileDialog',user);
    };

    const selectMenu = (item) => {
      emit('selectMenu', item.id);
    };

    return {
      username,
      identity,
      userRole,
      userAvatar, // 返回计算的头像路径
      activeMenu,
      filteredMenuItems,
      openProfileDialog,
      selectMenu,

    };
  },
  components: {
    UserFilled,
    Message,
    Tools,
    Goods,
    Service,
    Odometer,
    SwitchFilled,
    Histogram,
    HomeFilled,
    Promotion,
    InfoFilled,
    List,
    DocumentAdd,
    ShoppingCart,
  },
};
</script>



<style>
.sidebar {
  padding: 20px;
  width: 200px; /* 设置侧边栏宽度 */
  background-color: rgba(115, 117, 118, 0.9); /* 更加浓厚的背景色 */
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.4); /* 更深的阴影 */
  border-radius: 12px; /* 圆角 */
  height: 110cqh; /* 适应高度 */
  font-family: 'Courier New', Courier, monospace; /* 设置字体为等线字体 */
  color: #222424; /* 字体颜色 */
}

.profile-card {
  margin-bottom: 20px; /* 与菜单间隔 */
  text-align: center; /* 头像居中 */
  border: none; /* 去掉默认边框 */
  border-radius: 10px; /* 圆角 */
  background-color: rgba(246, 249, 250, 0.2) !important; /* 更加透明的半透明背景 */
}

.profile {
  display: flex;
  flex-direction: column; /* 垂直方向排列 */
  align-items: center; /* 水平居中对齐 */
  cursor: pointer;
  border-radius: 10px; /* 圆角 */
  transition: background-color 0.5s; /* 背景颜色过渡 */
}

.avatar {
  margin-bottom: 10px; /* 头像与文本之间的间距 */
  border: 2px solid #145984; /* 头像边框 */
  border-radius: 50%; /* 圆形头像 */
}

.profile-info {
  text-align: center; /* 文本居中 */
}

.username {
  font-weight: bold; /* 加粗字体 */
  color: #0d1618; /* 字体颜色 */
  display: block; /* 确保在新的一行显示 */
}

.identity {
  color: #f0eeee; /* 身份字体颜色，稍微浅一点 */
  display: block; /* 确保在新的一行显示 */
}



.el-menu-vertical {
  width: 100%; /* 菜单宽度 */
}

.menu-item {
  width: 100%; /* 菜单项宽度 */
  background-color: rgba(102, 103, 103, 0.5); /* 菜单项默认背景色 */
  color: #ebf0f0 !important; /* 默认字体颜色 */
  display: flex; /* Flex布局 */
  align-items: center; /* 垂直居中 */
  padding: 12px 0; /* 菜单项内边距 */

}

.menu-icon {
  font-size: 16px;          /* 控制图标大小（根据需要调整大小） */
  width: 16px;              /* 控制图标宽度 */
  height: 16px;             /* 控制图标高度 */
  margin-right: 4px;        /* 图标和文字之间的间距 */
  position: relative;       /* 相对定位使图标可以上移 */
  top: -2px;                /* 将图标上移 2px，根据需要微调 */
}

.menu-item:hover {
  background-color: rgba(255, 255, 255, 0.2); /* 悬停时背景色 */
  color: #040901 !important; /* 悬停时字体颜色 */

}

.menu-item.is-active {
  background-color: rgba(204, 195, 195, 0.3); /* 激活项背景色 */
  color: #409eff; /* 激活项字体颜色 */
  border-radius: 8px; /* 激活项圆角 */
}

/* 联系信息样式 */
.contact-info {
  margin-top: 50px; /* 与菜单间隔 */
  color: #ecf0f1; /* 联系信息字体颜色 */
}

.contact-item {
  display: flex;
  align-items: center; /* 垂直居中 */
  padding: 5px 0; /* 内边距 */
  font-size: 14px; /* 字体大小调整 */
}

.contact-icon {
  font-size: 16px; /* 图标大小 */
  margin-right: 10px; /* 图标与文本之间的间距 */
}


</style>
