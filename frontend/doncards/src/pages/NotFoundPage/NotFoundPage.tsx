import CustomAlert from "../../components/CustomAlert/CustomAlert";

function NotFoundPage() {
    return (
        <CustomAlert
            title="Page not found"
            description="The page you're looking for doesn't exist or may have been moved."
            actionLabel="Go to homepage"
            actionUrl="/"
        />
    );
}

export default NotFoundPage;