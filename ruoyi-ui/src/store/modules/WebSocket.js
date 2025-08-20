// store/webSocket.js
// import io from 'socket.io-client';
import store from "@/store/index.js";
import {ElNotification} from "element-plus";

const state = {
    socket: null,
};

const mutations = {
    SET_SOCKET(state, socket) {
        state.socket = socket;
    },
};


export function initWebSocket(token) {
    let userId = store.state.value.user.id
    // console.log("当前用户id：" + JSON.stringify(userId));
    // console.log("当前用户token：" + JSON.stringify(token));
    const url = `ws://127.0.0.1:8080/websocket/${userId}`; // 拼接用户ID到URL中
    const socket = new WebSocket(url);


    socket.onopen = () => {
        console.log('WebSocket connected');
    };

    socket.onclose = () => {
        console.log('WebSocket disconnected');
    };

    socket.onmessage = (event) => {
        console.log('收到消息：' + event.data);
        ElNotification({
            title: 'Success',
            message: event.data,
            type: 'success',
        });
    };

    socket.onerror = (error) => {
        console.error('WebSocket error:', error);
    };

    // 将socket对象存储到state中
    mutations.SET_SOCKET(state, socket);
}

export function closeWebSocket({state}) {
    if (state.socket) {
        state.socket.disconnect();
    }
}


export default {
    namespaced: true,
    state,
    mutations,
    // actions,
};