import { Button } from "react-bootstrap"
import { useNavigate } from "react-router-dom"


const Unauthorized = () => {
    const navigate = useNavigate()
    const goBack = () => navigate(-1)
    
    return (
        <section>
            <h1>Unauthorized</h1>
            <p>You do not have access to the request page.</p>
            <div className="flexGrow">
                <Button onClick={goBack}>Go Back</Button>
            </div>
        </section>
    )
}

export default Unauthorized