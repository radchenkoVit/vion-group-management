<template>
  <div>
      <nav class="navbar navbar-expand navbar-dark bg-dark">
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link to="/home" class="nav-link">Home</router-link>
        </li>
        <li class="nav-item" v-if="isAdmin">
          <router-link to="/admin" class="nav-link"> Admin Panel </router-link>
        </li>
      </div>

      <div class="navbar-nav ml-auto">
        <li class="nav-item">
          <span class="nav-link">{{ user.name }}</span>
        </li>
        <li class="nav-item">
          <a v-on:click="logout" class="nav-link"> LogOut </a>
        </li>
      </div>
      </nav>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import authService from '@/services/auth'

export default {
  name: 'Header',
  computed: {
    ...mapGetters([
      'user',
      'isAdmin'
    ])
  },
  created () {
    if (!this.user.authenticated) {
      this.$store.dispatch('getMyData')
    }
  },
  methods: {
    logout () {
      // this.$store.dispatch('logout')
      // this.$router.push({ name: 'LoginPage' })
      authService.logout().then(() => {
        this.$store.dispatch('logout')
        this.$router.push({ name: 'LoginPage' })
      }).catch((error) => {
        console.log(error)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.footer {
  width: 100%;
  font-size: 13px;
  color: #666;
  line-height: 40px;
  border-top: 1px solid #ddd;
  margin-top: 50px;

  .list-inline-item {
    margin-right: 10px;
  }

  a {
    color: #666;
  }
}
</style>
