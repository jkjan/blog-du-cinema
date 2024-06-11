import {Ref, ref} from "vue";
import {UserState} from "../../app/types.ts";

export const userState: Ref<UserState> = ref({
    isLoggedIn: false,
    nowUserData: {
        userId: "",
        username: "안녕하세요!",
        profileImage: null,
        jwtToken: ""
    }
})