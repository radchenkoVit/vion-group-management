import Role from '../model/role'

export const user = state => state.user

export const isAdmin = state => {
  return state.user.role === Role.ADMIN
}
