import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCoins } from '@fortawesome/free-solid-svg-icons';
import React from 'react';

export const CoinIcon = () => (
        <div className="w-auto h-4 mx-1 inline-flex pr-10">
            <FontAwesomeIcon icon={faCoins} />
        </div>
    )
;