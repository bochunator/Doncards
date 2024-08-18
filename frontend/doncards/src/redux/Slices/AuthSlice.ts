import { PayloadAction, createAsyncThunk, createSlice, isPending } from "@reduxjs/toolkit";
import axios, { AxiosError } from "axios";
import { CUSTOM_ALERTS, CustomAlertState, ApplicationUser, LoginPayload, RegistrationPayload, Role, defaultCustomAlertState, initialAuthState, AuthSliceState } from "../../types/authTypes";


const URL = import.meta.env.VITE_DONCARDS_BACKEND_URL

export const registerUser = createAsyncThunk(
    'auth/register',
    async (body: RegistrationPayload, thunkAPI) => {
        try {
            const response = await axios.post(`${URL}/auth/register`, body)
            return response.data
        } catch (e) {
            return thunkAPI.rejectWithValue((e as AxiosError).response?.data)
        }
    }
)

export const loginUser = createAsyncThunk(
    'auth/login',
    async (body: LoginPayload, thunkAPI) => {
        try {
            const response = await axios.post(`${URL}/auth/login`, body)
            return response.data
        } catch (e) {
            return thunkAPI.rejectWithValue((e as AxiosError).response?.data)
        }
    }
)

export const verifyUserByToken = createAsyncThunk(
    'auth/verify',
    async (token: string, thunkAPI) => {
        try {
            const response = await axios.get(`${URL}/auth/verify`, {
                headers: {
                    'Authorization': `Bearer ${token} `
                }
            })
            return response.data
        } catch (e) {
            return thunkAPI.rejectWithValue((e as AxiosError).response?.data)
        }
    }
)

export const getUserByUsername = createAsyncThunk(
    'auth/profile',
    async (username: string, thunkAPI) => {
        try {
            const response = await axios.get(`${URL}/auth/profile/${username}`)
            return response.data
        } catch (e) {
            return thunkAPI.rejectWithValue((e as AxiosError).response?.data)
        }
    }
)

const mapApplicationUser = (user: any): ApplicationUser => ({
    userId: user.userId,
    email: user.email,
    username: user.username,
    authorities: user.authorities.map((role: Role) => ({
        roleId: role.roleId,
        authority: role.authority
    })),
    enabled: user.enabled,
    credentialsNonExpired: user.credentialsNonExpired,
    accountNonExpired: user.accountNonExpired,
    accountNonLocked: user.accountNonLocked
})

const createRejectedHandler = (heading: string | null) => (state: AuthSliceState, action: PayloadAction<any>) => {
    state.error = action.payload
    state.loading = false
    state.customAlertState = {
        variant: CUSTOM_ALERTS.DANGER,
        heading,
        message: action.payload
    }
}

export const AuthSlice = createSlice({
    name: 'authentication',
    initialState: initialAuthState,
    reducers: {
        updateAlert(state, action: PayloadAction<CustomAlertState>) {
            return {
                ...state,
                customAlertState: action.payload
            }
        },
        updateRedirected(state, action: PayloadAction<boolean>) {
            return {
                ...state,
                redirected: action.payload
            }
        },
        updateJwt(state, action: PayloadAction<string>) {
            return {
                ...state,
                jwt: action.payload
            }
        },
        logoutUser(state) {
            return {
                ...state,
                jwt: null,
                applicationUser: null,
                customAlertState: { variant: CUSTOM_ALERTS.SUCCESS, heading: 'Logout', message: 'You have been logged out successfully.' }
            }
        }
    },
    extraReducers: (builder) => {
        builder.addCase(registerUser.fulfilled, (state, action) => {
            state.error = null
            state.loading = false
            state.customAlertState = { variant: CUSTOM_ALERTS.SUCCESS, heading: 'Registered', message: action.payload }
        })
        builder.addCase(loginUser.fulfilled, (state, action) => {
            state.error = null
            state.loading = false
            state.jwt = action.payload.jwt
            state.applicationUser = mapApplicationUser(action.payload.user)
        })
        builder.addCase(verifyUserByToken.fulfilled, (state, action) => {
            state.error = null
            state.loading = false
            state.applicationUser = mapApplicationUser(action.payload)
        })
        builder.addCase(getUserByUsername.fulfilled, (state, action) => {
            state.error = null
            state.loading = false
            state.profile = mapApplicationUser(action.payload)
        })
        builder.addCase(registerUser.rejected, createRejectedHandler('Registration Failed'))
        builder.addCase(loginUser.rejected, createRejectedHandler('Login Failed'))
        builder.addCase(verifyUserByToken.rejected, (state, action) => {
            state.error = action.payload as string
            state.loading = false
            localStorage.removeItem('jwt')
        })
        builder.addCase(getUserByUsername.rejected, createRejectedHandler('Doesn\'t found User'))
        builder.addMatcher(isPending, (state) => {
            state.error = null
            state.loading = true
            state.customAlertState = defaultCustomAlertState
        })
    },
})

export const { updateAlert, updateRedirected, updateJwt, logoutUser } = AuthSlice.actions

export default AuthSlice.reducer
