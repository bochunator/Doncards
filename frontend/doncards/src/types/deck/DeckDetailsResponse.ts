import type { AppUserSummaryResponse } from "../appUser/AppUserSummaryResponse";
import type { CardResponse } from "../card/CardResponse";

export type DeckDetailsResponse = {
    id: number;
    name: string;
    description: string;
    createdAt: string;
    viewCount: number;
    appUserSummaryResponse: AppUserSummaryResponse;
    cardResponses: CardResponse[];
}