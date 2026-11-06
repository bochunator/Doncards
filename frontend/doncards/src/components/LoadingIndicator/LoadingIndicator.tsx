import './LoadingIndicator.css';

type LoadingIndicatorProps = {
    message?: string;
};

function LoadingIndicator({
    message = 'Loading',
}: LoadingIndicatorProps) {
    return (
        <div className="loading-indicator" role="status">
            <img
                className="loading-indicator__animation"
                src={`${import.meta.env.BASE_URL}/images/train-loader.gif`}
                alt=""
                aria-hidden="true"
            />
            <span className="loading-indicator__message">{message}</span>
        </div>
    );
}

export default LoadingIndicator;
