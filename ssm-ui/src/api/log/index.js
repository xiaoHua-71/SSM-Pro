import request from "@/api";

export function listLog(data) {
    return request({
        url: '/xhOperLog',
        method: 'get',
        params: data
    })
}