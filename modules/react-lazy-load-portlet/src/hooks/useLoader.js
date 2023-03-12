import useIsMounted from './useIsMounted';
import {useCallback, useRef} from 'react';

const EmptyModule = {default: () => null};

export default function useLoader() {
    const modulesRef = useRef(new Map());
    const isMounted = useIsMounted();

    return useCallback(
        (module) => {
            if (!modulesRef.current.has(module)) {
                modulesRef.current.set(
                    module,
                    new Promise((resolve) => {
                        Liferay.Loader.require(
                            [module],
                            (Module) => {
                                if (isMounted()) {
                                    resolve(Module ? Module : EmptyModule);
                                }
                            },
                            (error) => {
                                console.error(error);
                                if (isMounted()) {
                                    resolve(EmptyModule);
                                }
                            }
                        );
                    })
                );
            }

            return modulesRef.current.get(module);
        },
        [isMounted]
    );
}
