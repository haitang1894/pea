import { request } from '../request';

/** get role list */
export function fetchGetRoleList(params?: Api.SystemManage.RoleSearchParams) {
  return request<Api.SystemManage.RoleList>({
    url: '/systemManage/getRoleList',
    method: 'get',
    params
  });
}

/**
 * get all roles
 *
 * these roles are all enabled
 */
export function fetchGetAllRoles() {
  return request<Api.SystemManage.AllRole[]>({
    url: '/systemManage/getAllRoles',
    method: 'get'
  });
}

/**
 * add Role
 *
 * @param sysRole role info
 */
export function addRole(sysRole: Api.SystemManage.Role) {
  return request({
    url: '/systemManage/addRole',
    method: 'post',
    data: sysRole
  });
}

/** get user list */
export function fetchGetUserList(params?: Api.SystemManage.UserSearchParams) {
  return request<Api.SystemManage.UserList>({
    url: '/systemManage/getUserList',
    method: 'get',
    params
  });
}

/**
 * create user
 *
 * @param sysRole role info
 */
export function createUser(user: Api.SystemManage.User) {
  return request({
    url: '/systemManage/createUser',
    method: 'post',
    data: user
  });
}

/**
 * update user
 *
 * @param sysRole role info
 */
export function updateUser(user: Api.SystemManage.User) {
  return request({
    url: '/systemManage/update',
    method: 'post',
    data: user
  });
}

/** add menu */
export function addResource(params?: Api.SystemManage.Menu) {
  return request({
    url: '/sys/resource/add',
    method: 'post',
    data: params
  });
}

/** get menu list */
export function fetchGetMenuList() {
  return request<Api.SystemManage.MenuList>({
    url: '/systemManage/getMenuList/v2',
    method: 'get'
  });
}

/** get all pages */
export function fetchGetAllPages() {
  return request<string[]>({
    url: '/systemManage/getAllPages',
    method: 'get'
  });
}

/** get menu tree */
export function fetchGetMenuTree() {
  return request<Api.SystemManage.MenuTree[]>({
    url: '/systemManage/getMenuTree',
    method: 'get'
  });
}

/** get role resource id */
export function getRoleResourceId(roleId: number) {
  return request({
    url: '/systemManage/getRoleResourceId',
    method: 'post',
    data: roleId
  });
}

/** update role resource info */
export function updateRoleResourceInfo(roleId: number, resourceId: number[]) {
  return request({
    url: '/systemManage/updateRoleResourceInfo',
    method: 'post',
    data: {
      roleId,
      resourceId
    }
  });
}
