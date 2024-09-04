

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
    authorities: Role[]
    email: string
    username: string
    enabled: boolean
    credentialsNonExpired: boolean
    accountNonExpired: boolean
    accountNonLocked: boolean
}

export const mapApplicationUser = (user: any): ApplicationUser => ({
    userId: user.userId,
    authorities: user.authorities.map((role: Role) => ({
        roleId: role.roleId,
        authority: role.authority
    })),
    email: user.email,
    username: user.username,
    accountNonExpired: user.accountNonExpired,
    accountNonLocked: user.accountNonLocked,
    credentialsNonExpired: user.credentialsNonExpired,
    enabled: user.enabled
})
