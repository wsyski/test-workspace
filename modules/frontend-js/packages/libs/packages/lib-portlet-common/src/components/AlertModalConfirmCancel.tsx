import ClayModal, {useModal} from '@clayui/modal';
import React, {useState} from 'react';
import {WithTranslation, withTranslation} from 'react-i18next';

import LiferayUtil from '../utils/LiferayUtil';
import {AlertConfirmProps} from './AlertConfirm';


export interface Props extends WithTranslation, AlertConfirmProps {
}


const AlertModalConfirmCancelWithT: React.FC<Props> = ({
                                                           alert,
                                                           onConfirm,
                                                           t,
                                                       }: Props) => {
    const [visible, setVisible] = useState(true);
    const {observer, onClose} = useModal({
        onClose: () => setVisible(false),
    });
    const title =
        typeof alert.title === 'undefined'
            ? t(`lblAlert.${alert.status}`)
            : alert.title;

    const confirm = () => {
        onConfirm(alert);
        onClose();
    }

    if (visible) {
        return (
            <ClayModal
                className="modal-dialog-centered"
                observer={observer}
                spritemap={LiferayUtil.getClaySpritemap()}
                status={alert.status}
            >

                <ClayModal.Header aria-live="polite">{title}</ClayModal.Header>

                <ClayModal.Body aria-live="polite">
                    <ClayModal.Subtitle>
                        {alert.message}
                    </ClayModal.Subtitle>
                </ClayModal.Body>

                <div className="modal-footer">
                    <button
                        autoFocus
                        className="btn btn-primary"
                        data-dismiss="modal"
                        onClick={onClose}
                        type="button"
                    >
                        {t('btnCancel.label')}
                    </button>

                    <button
                        className="btn btn-secondary"
                        onClick={confirm}
                        type="button">
                        {t('btnOK.label')}
                    </button>
                </div>
            </ClayModal>

        )
    }

    return null;
};

export default withTranslation()(
    AlertModalConfirmCancelWithT
) as React.FC<AlertConfirmProps>;
