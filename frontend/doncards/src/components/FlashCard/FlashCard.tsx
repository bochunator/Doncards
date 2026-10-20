import { useRef, useState } from 'react';
import type { PointerEvent } from 'react';
import type { CardResponse } from '../../types/card/CardResponse';
import './FlashCard.css';

type Props = {
    card: CardResponse;
    index: number;
    total: number;
    onAnswer: (knewAnswer: boolean) => void;
};

function FlashCard({ card, index, total, onAnswer }: Props) {
    const [isFlipped, setIsFlipped] = useState(false);
    const [swipeOffset, setSwipeOffset] = useState(0);

    const pointerStartX = useRef(0);
    const isDragging = useRef(false);
    const lastSwipeAt = useRef(0);

    function handlePointerDown(event: PointerEvent<HTMLButtonElement>) {
        pointerStartX.current = event.clientX;
        isDragging.current = true;
        event.currentTarget.setPointerCapture(event.pointerId);
    }

    function handlePointerMove(event: PointerEvent<HTMLButtonElement>) {
        if (!isDragging.current) {
            return;
        }

        setSwipeOffset(event.clientX - pointerStartX.current);
    }

    function handlePointerUp() {
        if (!isDragging.current) {
            return;
        }

        isDragging.current = false;

        if (swipeOffset <= -100) {
            lastSwipeAt.current = Date.now();
            setSwipeOffset(-window.innerWidth);
            onAnswer(false);
            return;
        }

        if (swipeOffset >= 100) {
            lastSwipeAt.current = Date.now();
            setSwipeOffset(window.innerWidth);
            onAnswer(true);
            return;
        }

        setSwipeOffset(0);
    }

    function handleClick() {
        if (Date.now() - lastSwipeAt.current < 400) {
            return;
        }

        setIsFlipped((previous) => !previous);
    }

    return (
        <button
            type="button"
            className={`flashcard ${isFlipped ? "flashcard--flipped" : ""}`}
            style={{
                transform: `translateX(${swipeOffset}px) rotate(${swipeOffset * 0.04}deg)`,
                transition: isDragging.current
                    ? "none"
                    : "transform 0.2s ease",
            }}
            onClick={handleClick}
            onPointerDown={handlePointerDown}
            onPointerMove={handlePointerMove}
            onPointerUp={handlePointerUp}
            onPointerCancel={() => {
                isDragging.current = false;
                setSwipeOffset(0);
            }}
        >
            <span className="flashcard__inner">
                <span className="flashcard__face flashcard__front">
                    <span className="flashcard__label">
                        WORD
                    </span>

                    <span className="flashcard__word">
                        {card.term}
                    </span>

                    <span className="flashcard__hint">
                        Click to reveal the translation
                    </span>
                </span>

                <span className="flashcard__face flashcard__back">
                    <span className="flashcard__label">
                        TRANSLATION
                    </span>

                    <span className="flashcard__word">
                        {card.translation}
                    </span>

                    <span className="flashcard__hint">
                        Did you know the answer?
                    </span>
                </span>
            </span>

            <span className="flashcard__counter">
                {index + 1} / {total}
            </span>
        </button>
    );
}

export default FlashCard;