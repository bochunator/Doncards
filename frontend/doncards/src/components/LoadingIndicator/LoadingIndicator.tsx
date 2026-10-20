import './LoadingIndicator.css';

type LoadingIndicatorProps = {
    message?: string;
};

function LoadingIndicator({
    message = 'Loading...',
}: LoadingIndicatorProps) {
    return (
        <div className="loading-indicator" role="status">
            <span
                className="loading-indicator__spinner"
                aria-hidden="true"
            />
            <span>{message}</span>
        </div>
    );
}

export default LoadingIndicator;
