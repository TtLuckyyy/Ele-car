<template>
  <div class="home">
    <NavBar />
    <el-row>
      <el-col :span="4">
        <Sidebar @openProfileDialog="openProfileDialog" @selectMenu="selectMenu" />
      </el-col>
      <el-col :span="20" class="content">
        <div class="main-content">
          <!-- 动态显示组件 -->
          <HomeView @BrowseAnuceDetail="selectMenu" v-if="selectedMenu === 1" />
          <VehicleRegistrationForm v-if="selectedMenu === 2" />
          <Map v-if="selectedMenu === 3" />
          <ChargepileReservation v-if="selectedMenu === 4" />
          <BrowseAnnouncement v-if="selectedMenu === 5" />
          <HomeView @BrowseAnuceDetail="selectMenu" v-if="selectedMenu === 6" />
          <UserManagement v-if="selectedMenu === 7"/>
          <AdminRegistrationForm v-if="selectedMenu === 8" />
          <AdminChargePileManagement v-if="selectedMenu === 9" />
          <AdminUserFeedBack v-if="selectedMenu === 10" />
          <AnnouncementPublish v-if="selectedMenu === 11" />
          <Map v-if="selectedMenu === 12" />
          <HomeView @BrowseAnuceDetail="selectMenu" v-if="selectedMenu === 13" />
          <TraderRegistrationForm v-if="selectedMenu === 14" />
          <ChargingPile v-if="selectedMenu === 15" />
          <ChargingPileManagement v-if="selectedMenu === 16" />
          <TraderUserFeedBack v-if="selectedMenu === 17" />
          <Map v-if="selectedMenu === 18" />
          <BrowseAnnouncement v-if="selectedMenu === 19" />
          <BrowseReservation v-if="selectedMenu === 20" />
          <AdminBrowseReservation v-if="selectedMenu === 21" />
          <TraderBrowseReservation v-if="selectedMenu === 22" />
        </div>
      </el-col>
    </el-row>
    <UserProfileDialog 
      :dialogVisible="dialogVisible" 
      :currentUser="currentUser" 
      @closedialogvisible="closedialogvisible" 
    />
  </div>
</template>

<script>
import Sidebar from '../components/Home/Sidebar.vue';
import NavBar from '../components/Home/Navbar.vue';
import UserProfileDialog from '../components/User/UserProfileDialog.vue';
import UserManagement from '../components/User/UserManagement.vue';
import VehicleRegistrationForm from '../components/CarOwner/VehicleRegistrationForm.vue';
import TraderRegistrationForm from '@/components/Trader/TraderRegistrationForm.vue';
import ChargingPile from '@/components/Trader/ChargingPile.vue';
import ChargingPileManagement from '@/components/Trader/ChargingPileManagement.vue';
import ChargepileReservation from '@/components/CarOwner/ChargepileReservation.vue';
import AdminRegistrationForm from '@/components/Admin/AdminRegistrationForm.vue';
import AdminChargePileManagement from '@/components/Admin/AdminChargePileManagement.vue';
import BrowseReservation from '@/components/CarOwner/BrowseReservation.vue';
import AdminBrowseReservation from '@/components/Admin/AdminBrowseReservation.vue';
import TraderBrowseReservation from '@/components/Trader/TraderBrowseReservation.vue';
import TraderUserFeedBack from '@/components/Trader/TraderUserFeedBack.vue';
import AdminUserFeedBack from '@/components/Admin/AdminUserFeedBack.vue';
import AnnouncementPublish from '@/components/Admin/AnnouncementPublish.vue';
import BrowseAnnouncement from '@/components/CarOwner/BrowseAnnouncement.vue';
import HomeView from '@/components/Home/HomeView.vue';
import Map from '@/components/Map/Map.vue';

export default {
  components: {
    Sidebar,
    NavBar,
    UserProfileDialog,
    UserManagement,
    VehicleRegistrationForm,
    TraderRegistrationForm,
    AdminRegistrationForm,
    ChargingPile,
    ChargingPileManagement,
    AdminChargePileManagement,
    ChargepileReservation,
    BrowseReservation,
    AdminBrowseReservation,
    TraderBrowseReservation,
    TraderUserFeedBack,
    AdminUserFeedBack,
    AnnouncementPublish,
    BrowseAnnouncement,
    HomeView,
    Map,
  },
  data() {
    return {
      dialogVisible: false,
      selectedMenu: 1, // 当前选择的菜单项
      currentUser: null,
    };
  },
  methods: {
    /************************** 页面显示****************************/
    openProfileDialog(user = null) {
      this.currentUser = user
        ? { ...user }
        : {
            id: 'Null',
            identity: 'Null',
            gender: 'Null',
            phone: 'Null',
            username: 'Null',
            password: 'Null',
            email: 'Null',
            status: 'Null',
          };
      this.dialogVisible = true;
    },
    closedialogvisible(visible) {
      this.dialogVisible = visible;
    },
    selectMenu(index) {
      this.selectedMenu = parseInt(index); // 设置当前选择的菜单项
    },
  },
};
</script>

<style>
.home {
  padding-top: 60px;
}
.content {
  padding: 20px;
}
.main-content {
  padding: 20px;
}
</style>
