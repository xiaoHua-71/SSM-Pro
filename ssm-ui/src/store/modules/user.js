import {
	login,logout,getInfo
} from '@/api/user/index.js'

//只有一个export就不需要解构
import storage from '@/util/storage.js'
const user = {
	state: {
		username: '',
		nickname: '',
		token: '',
		roles:[],
		permissions:[]
	},
	getters: {
		isLogin(state){
			return state.username !== '' && state.token !=='';
		},
		permission(state){
			return state.permissions;
		},
		roles(state){
			return state.roles;
		}
	},
	mutations: {

		SAVE_USERNAME(state, username) {
			state.username = username;
		},
		SAVE_NICKNAME(state, nickname) {
			state.nickname = nickname;
		},
		SAVE_TOKEN(state, token) {
			state.token = token;
		},
		SAVE_ROLES(state, roles) {
			state.roles = roles;
		},
		SAVE_PERMISSIONS(state, permissions) {
			state.permissions = permissions;
		}
	},
	actions: {
		LOGIN({
			commit
		}, user) {
			return new Promise(function(resolve) {
				login(user).then(res => {
					console.log(res.data.xhUser.userName);
					//需要将获取的数据保存起来
					commit("SAVE_USERNAME", res.data.xhUser.userName);
					commit("SAVE_NICKNAME", res.data.xhUser.nickName);
					commit("SAVE_TOKEN", res.data.token);
					console.log("Saving user data to session:", res.data);
					storage.saveSessionObject("loginUser", res.data);
					
					
					                
					resolve(res);
				}).catch(error => {
                console.error("Login failed:", error);
                throw error; // 重新抛出错误以便处理
            });
			});

		},
		GET_INFO({ commit }){
			return new Promise(resolve => {
				getInfo().then(res =>{
					commit("SAVE_ROLES",res.data.roles);
					commit("SAVE_PERMISSIONS",res.data.perms);
					resolve();
				})
			})
		},
		LOGOUT({commit}) {
			return new Promise(function(resolve) {
				logout().then(res => {
					//需要将获取的数据保存起来
					commit("SAVE_USERNAME", '');
		
					commit("SAVE_NICKNAME", '');
		
					commit("SAVE_TOKEN", '');
					storage.remove("loginUser");
					resolve(res);
				})
			})
		
		},
		RECOVER_USER({commit}) {
		//从store中获取数据
		let loginUser = storage.getSessionObject("loginUser");
		if(loginUser){
			commit("SAVE_USERNAME", loginUser.xhUser.userName);
					
			commit("SAVE_NICKNAME", loginUser.xhUser.nickName);
					
			commit("SAVE_TOKEN", loginUser.token);
		}
		
		}
		
	}
}

export default user
