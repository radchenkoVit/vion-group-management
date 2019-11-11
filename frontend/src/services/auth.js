import axios from 'axios'
import { BehaviorSubject } from 'rxjs'
import { TOKEN_NAME, ROLES } from '../model/constants'

// eslint-disable-next-line no-unused-vars// eslint-disable-next-line no-undef
const authenticatedSubject = new BehaviorSubject(false)
const currentUserToken = new BehaviorSubject(JSON.stringify(localStorage.getItem(TOKEN_NAME)))
class AuthService {
  get userToken () {
    return currentUserToken.value
  }

  get userTokenObserver () {
    return currentUserToken.asObserver()
  }

  get userRoles () {
    return localStorage.getItem(ROLES)
  }

  get isAuthenticated () {
    return authenticatedSubject.value
  }

  login (user) {
    return axios.post('/api/auth/login', user).then(response => {
      let token = response.headers['authorization']
      let role = response.headers[ROLES]

      localStorage.setItem(TOKEN_NAME, token)
      localStorage.setItem(ROLES, role)
      currentUserToken.next(token)
      authenticatedSubject.next(true)
    })
  }

  logout () {
    // return axios.post('/api/auth/logout').then(response => {
    localStorage.removeItem(TOKEN_NAME)
    localStorage.removeItem(ROLES)
    currentUserToken.next(null)
    authenticatedSubject.next(false)
    // })
  }
}

export default new AuthService()
