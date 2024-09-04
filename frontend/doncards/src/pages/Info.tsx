

function Info() {
    return (
        <>
            <h2>Info</h2>
            <section>
                <h3>Currently Works</h3>
                <ul>
                    <li>Registration</li>
                    <li>Login</li>
                    <li>Deck Management
                        <ul>
                            <li>Create deck if logged in</li>
                        </ul>
                    </li>
                    <li>Logout</li>
                    <li>Home - The most extensive part of the application
                        <ul>
                            <li>View created decks</li>
                            <li>Check deck authors and their decks</li>
                            <li>Start a learning session (results are still being developed)</li>
                        </ul>
                    </li>
                </ul>

                <h3>Doesn't Work / TODO</h3>
                <ul>
                    <li>User panel
                        <ul>
                            <li>Edit user information</li>
                            <li>Edit decks</li>
                        </ul>
                    </li>
                    <li>Admin panel
                        <ul>
                            <li>Edit user information</li>
                        </ul>
                    </li>
                </ul>
            </section>
        </>
    )
}

export default Info