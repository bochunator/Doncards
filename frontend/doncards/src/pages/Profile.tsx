import React, { useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { AppDispatch, RootState } from '../redux/Store'
import { useDispatch, useSelector } from 'react-redux'
import { getUserByUsername } from '../redux/Slices/AuthSlice'


const Profile: React.FC = () => {
    const { username } = useParams()
    const dispatch: AppDispatch = useDispatch()
    const profile = useSelector((state: RootState) => state.auth.profile)

    useEffect(() => {
        if (username) {
            dispatch(getUserByUsername(username))
        }
    }, [])
    return (
        <>
            <h2>Profile</h2>
            <h3>{profile ? profile.username : 'Not found'}</h3>
            <p>ID: {profile ? profile.userId : 'Not found'}</p>
            <p>{profile ? profile.email : 'Not found'}</p>
        </>
    )
}

export default Profile