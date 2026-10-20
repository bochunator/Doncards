import { Link } from 'react-router-dom';
import './CustomAlert.css';

type CustomAlertProps = {
    title: string;
    description: string;
} & (
    | {
          actionLabel: string;
          actionUrl: string;
          onAction?: never;
      }
    | {
          actionLabel: string;
          onAction: () => void;
          actionUrl?: never;
      }
    | {
          actionLabel?: never;
          actionUrl?: never;
          onAction?: never;
      }
);

function CustomAlert({
    title,
    description,
    actionLabel,
    actionUrl,
    onAction,
}: CustomAlertProps) {
    return (
        <section className="custom-alert">
            <div className="custom-alert__message">
                <h1>{title}</h1>
                <p>{description}</p>

                {actionLabel && onAction && (
                    <button
                        className="custom-alert__back"
                        type="button"
                        onClick={onAction}
                    >
                        {actionLabel}
                    </button>
                )}

                {actionLabel && actionUrl && !onAction && (
                    <Link
                        className="custom-alert__back"
                        to={actionUrl}
                    >
                        {actionLabel}
                    </Link>
                )}
            </div>
        </section>
    )
}

export default CustomAlert;