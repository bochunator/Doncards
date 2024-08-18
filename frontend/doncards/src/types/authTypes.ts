export interface RegistrationPayload {
    email: string
    username: string
    password: string
}

export const defaultRegistrationPayload: RegistrationPayload = {
    email: '',
    username: '',
    password: ''
}

export interface RegistrationData {
    registrationPayload: RegistrationPayload
    matchPassword: string
}

export const defaultRegistrationData: RegistrationData = {
    registrationPayload: defaultRegistrationPayload,
    matchPassword: ''
}

export interface LoginPayload {
    identifier: string
    password: string
}

export const defaultLoginPayload: LoginPayload = {
    identifier: '',
    password: ''
}

export interface LoginData {
    loginPayload: LoginPayload
}

export const defaultLoginData: LoginData = {
    loginPayload: defaultLoginPayload
}

export enum ROLES {
    USER = 'USER',
    ADMIN = 'ADMIN'
}

export type RoleType = ROLES

export interface Role {
    roleId: number
    authority: RoleType
}

export interface ApplicationUser {
    userId: number
    email: string
    username: string
    authorities: Role[]
    enabled: boolean
    credentialsNonExpired: boolean
    accountNonExpired: boolean
    accountNonLocked: boolean
}

export enum CUSTOM_ALERTS {
    PRIMARY = 'primary',
    SECONARY = 'secondary',
    SUCCESS = 'success',
    DANGER = 'danger',
    WARNING = 'warning',
    INFO = 'info',
    LIGHT = 'light',
    DARK = 'dark'
}

export type CustomAlertType = CUSTOM_ALERTS

export interface CustomAlertState {
    variant: CustomAlertType
    heading: string | null
    message: string | null
}

export const defaultCustomAlertState: CustomAlertState = {
    variant: CUSTOM_ALERTS.SUCCESS,
    heading: null,
    message: null
}

export interface AuthSliceState {
    error: string | null
    loading: boolean
    customAlertState: CustomAlertState
    jwt: string | null
    applicationUser: ApplicationUser | null
    redirected: boolean
    profile: ApplicationUser | null
}

export const initialAuthState: AuthSliceState = {
    error: null,
    loading: false,
    customAlertState: defaultCustomAlertState,
    jwt: null,
    applicationUser: null,
    redirected: false,
    profile: null
}
