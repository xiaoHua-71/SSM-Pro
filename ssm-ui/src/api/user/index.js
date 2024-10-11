import request from "@/api";

// // 新增用户
// // 查询用户列表
// export function listUser(query) {
//     return request({
//         url: '/user',
//         method: 'get',
//         params: query
//     })
// }

// // 新增用户
// export function addUser(data) {
//     return request({
//         url: '/user',
//         method: 'post',
//         data: data
//     })
// }

export function login(data) {
    return request({
        url: '/login',
        method: 'post',
        data: data
    })
}
//新增用户
export function add(data) {
    return request({
        url: '/xhUser',
        method: 'post',
        data: data
    })
}

//修改用户
export function update(data) {
    return request({
        url: '/xhUser',
        method: 'put',
        data: data
    })
}

export function logout() {
    return request({
        url: '/logout',
        method: 'get'
    })
}

export function listUser(data) {
    return request({
        url: '/xhUser',
        method: 'get',
        params: data
    })
}

export function getInfo() {
    return request({
        url: '/xhUser/getInfo',
        method: 'get'
    })
}

export function getById(id) {
    return request({
        url: '/xhUser/' + id,
        method: 'get'
    })
}

export function deleteUser(id) {
    return request({
        url: '/xhUser/' + id,
        method: 'delete'
    })
}
	
