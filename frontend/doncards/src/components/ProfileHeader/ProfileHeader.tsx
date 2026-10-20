
import type { AppUserDetailsResponse } from '../../types/appUser/AppUserDetailsResponse';
import './ProfileHeader.css'

type Props = {
    appUser: AppUserDetailsResponse;
};

function ProfileHeader({ appUser }: Props) {
    const initial = appUser.username.trim().charAt(0).toUpperCase();

    const createdAt =  new Intl.DateTimeFormat("en-US", {
        day: "numeric",
        month: "long",
        year: "numeric",
    }).format(new Date(appUser.createdAt));
    return (
        <header className='profile-header'>
            <div className='profile-header__identity'>
                <div className='profile-header__avatar' aria-hidden='true'>
                    {initial}
                </div>

                <div className='profile-header__info'>
                    <h1 className='profile-header__username'>
                        {appUser.username}
                    </h1>
                    <p className='profile-header__joined'>
                        Member of Doncards since {createdAt}
                    </p>
                </div>
            </div>
            <div className='profile-header__stats'>
                <div className='profile-header__stat'>
                    <span className='profile-header__stat-value'>
                        {appUser.deckCount}
                    </span>

                    <span className='profile-header__stat-label'>
                        created decks
                    </span>
                </div>
                <div className='profile-header__stat'>

                    <span className='profile-header__stat-value'>
                        {appUser.cardCount}
                    </span>

                    <span className='profile-header__stat-label'>
                        created cards
                    </span>
                </div>
            </div>
        </header>
    )
}

export default ProfileHeader;
