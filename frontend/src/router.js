import Vue from 'vue'
import Router from 'vue-router'
import LoginPage from '@/views/LoginPage'
import RegisterPage from '@/views/RegisterPage'
import HomePage from '@/views/HomePage'
import AdminPage from '@/views/AdminPage'
import NotFoundPage from '@/views/NotFound'
import Role from '@/model/role'
import authService from '@/services/auth'
import UnauthorizedPage from '@/views/UnathorizedPage'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [{
    path: '/',
    name: 'LoginPage',
    component: LoginPage
  }, {
    path: '/login',
    component: LoginPage
  }, {
    path: '/registration',
    name: 'RegisterPage',
    component: RegisterPage
  }, {
    path: '/home',
    name: 'HomePage',
    component: HomePage,
    meta: { roles: [Role.ADMIN, Role.USER] }
  }, {
    path: '/admin',
    name: 'AdminPage',
    component: AdminPage,
    meta: { roles: [Role.ADMIN] }
  }, {
    path: '/unauthorized',
    name: 'unauthorized',
    component: UnauthorizedPage
  }, {
    path: '/404',
    component: NotFoundPage
  }, {
    path: '*', redirect: '/404'
  }]
}
)

router.beforeEach((to, from, next) => {
  // TODO, HOW to get info from vuex
  const { roles } = to.meta
  const authenticated = authService.authenticatedSubject ? authService.authenticatedSubject.value : null
  const currentUserRole = authService.userRoles ? authService.userRoles : null
  const currentUserToken = authService.userToken

  console.log('authificated: ' + authenticated)

  if (authenticated === false) {
    console.log('forward to login')
    return next({ path: '/login' })
  }
  console.log('currentUserToken: ' + currentUserToken)
  console.log('currentUserRole: ' + currentUserRole)

  // eslint-disable-next-line eqeqeq, will be okey if values Falthy type
  if (currentUserToken == false || currentUserRole == false) {
    console.log('forward to login')
    return next({ path: '/login' })
  }

  if (roles) {
    if (!roles.includes(currentUserRole)) {
      console.log('forward to unauthorized')
      return next({ name: 'unauthorized' })
    }
  }

  next()
})

export default router
