import meService from '@/services/meservice'

export const getMyData = ({ commit }) => {
  meService.getMyData().then(data => {
    commit('updateMyData', data)
  })
}

export const logout = ({ commit }) => {
  commit('logout')
}
