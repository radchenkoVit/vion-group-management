import { TOKEN_NAME, ROLES } from '@/model/constants'

export default {
  updateMyData (state, data) {
    state.user.name = data.name
    state.user.authenticated = true
    state.user.role = localStorage.getItem(ROLES)
  },
  logout (state) {
    localStorage.removeItem(TOKEN_NAME)
    localStorage.removeItem(ROLES)
    state.user.name = ''
    state.user.authenticated = false
    state.user.role = ''
  }
}
