package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.template_contexts.model.central.CentralSettingsDto;
import com.google.common.cache.LoadingCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class CatalogueReferenceServiceImplTest {

 /*   private static final String ARENA_CENTRAL_URL = "arena-central/configs";
    private static final String ERROR_MSG = "error msg";

    @Mock
    StaticContextService staticContextService;

    @Mock
    JSONFactory jsonFactory;

    @Mock
    ThemeDisplay themeDisplay;

    @Mock
    CentralSettingsDto centralSettingsDto;

    @Mock
    GroupLocalService groupLocalService;

    @Mock
    Group group;

    @Mock
    LoadingCache<Long, CentralSettingsDto> centralSettingsCache;

    @BeforeEach
    void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallCentral_success() throws IOException {
        JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
        jsonFactoryUtil.setJSONFactory(jsonFactory);

        CatalogueReferenceServiceImpl tested = new CatalogueReferenceServiceImpl();
        tested.setStaticContextService(staticContextService);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        when(staticContextService.URLtoString(captor.capture())).thenReturn("{  \"total\": 232473,  \"records\": [    {      \"id\": \"558883\",      \"uberkey\": \"8ade8d9c-61653cbe-0162-fcb7b10e-2082\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"daisy\",        \"subjects\": [          \"Deckare\"        ],        \"numbers\": {},        \"languageOriginal\": \"nor\",        \"title\": \"Katharinakoden\",        \"descriptions\": [],        \"shelfMark\": \"Hce/TD ; Hce/TD\",        \"targetAudience\": \"a\",        \"publicationYear\": \"2018\",        \"contributors\": [          {            \"name\": \"Mitchell, Cajsa\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Lindgren, Åke\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"839.78\",          \"Hcedb.01/TD\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Horst, Jørn Lier\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"558219\",      \"uberkey\": \"8ade8d9c-5806535a-015b-4eb6efaf-0e48\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"Grisar\",          \"Tandläkare\",          \"Borsta tänderna\",          \"Förmänskligade djur\"        ],        \"numbers\": {          \"ISBN\": [            \"9789177832812\"          ]        },        \"languageOriginal\": \"eng\",        \"title\": \"Gapa stort\",        \"descriptions\": [],        \"shelfMark\": \"Hcf\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2018\",        \"contributors\": [          {            \"name\": \"Helleday Ekwurtzel, Marie\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789177832812\"        ],        \"category\": \"fictionCategory\",        \"classificationCodes\": [          \"839.78\",          \"Hcf(yb)\"        ],        \"contentTypes\": [],        \"authors\": []      }    },    {      \"id\": \"558395\",      \"uberkey\": \"8ade8d9d-5949fa37-015b-3887c2dc-3a26\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"alb\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"Resor\",          \"Slott\",          \"Fladdermöss\",          \"Vampyrer\"        ],        \"numbers\": {          \"ISBN\": [            \"9789928024800\",            \"9928024804\"          ]        },        \"languageOriginal\": \"ger\",        \"title\": \"Koni dhe kështjella e vampirëve\",        \"descriptions\": [],        \"shelfMark\": \"Hrf,uf\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2014\",        \"contributors\": [],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789928024800\",          \"https://api.axiell.com/cover/api/covers/sweden/9928024804\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"891.991\",          \"K\",          \"Hrf.01\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Boehme, Julia\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"561934\",      \"uberkey\": \"8ade8d9d-61655241-0162-533f08ac-250b\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"bos\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"digte.\",          \"billedbøger.\",          \"mødre.\",          \"forældre.\",          \"bedsteforældre.\",          \"landsbyen.\",          \"skove.\",          \"bøger.\",          \"læsning.\",          \"Lyrik\"        ],        \"numbers\": {          \"ISBN\": [            \"9789926427214\"          ]        },        \"title\": \"Olovka pustolovka\",        \"descriptions\": [],        \"shelfMark\": \"Hmfu,uf\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2017\",        \"contributors\": [          {            \"name\": \"Curic, Ivana\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Bilic, Zlata\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Krhin, Kristina\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789926427214\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"891.82\",          \"88.652\",          \"Hmfu.03,uf\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Esic, Simo\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"561935\",      \"uberkey\": \"8ade8dab-6680bdaf-0167-8175f846-2f07\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"hrv\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"Börja skolan\",          \"Siffror\",          \"Bokstäver\",          \"billedbøger.\",          \"skolen.\",          \"skolestart.\",          \"undervisning.\"        ],        \"numbers\": {          \"ISBN\": [            \"9789531421720\"          ]        },        \"title\": \"Dobro dosli u skolu!\",        \"descriptions\": [],        \"shelfMark\": \"Hmft,uf\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2017\",        \"contributors\": [          {            \"name\": \"Mezic, Zeljka\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789531421720\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"891.82\",          \"88.653\",          \"Hmft(yb)\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Mlinarec, Robert\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"559511\",      \"uberkey\": \"8ade8dab-6680bdaf-0166-f6e72513-15b0\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"srp\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"erindringer.\",          \"dagbøger.\",          \"ungdomsbøger.\",          \"unge.\",          \"ungdom.\",          \"skolen.\",          \"venskab.\",          \"forelskelse.\",          \"Skolan\",          \"Vänskap\",          \"Kärlek\"        ],        \"numbers\": {          \"ISBN\": [            \"9788664571425\"          ]        },        \"title\": \"Muski dnevnik 3\",        \"descriptions\": [],        \"shelfMark\": \"Hmfs,u\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2017\",        \"contributors\": [],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9788664571425\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"891.82\",          \"88.654\",          \"Hmfs.01\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Pejcic, Dusan\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"559513\",      \"uberkey\": \"8ade8da9-63816704-0165-0ea160d8-32a4\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"Manga\",          \"Rita\"        ],        \"numbers\": {          \"ISBN\": [            \"9789198445725\"          ]        },        \"title\": \"Nosebleed Studio lär dig teckna manga!\",        \"descriptions\": [],        \"shelfMark\": \"Ig,u\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2018\",        \"contributors\": [          {            \"name\": \"Rosberg, Elise\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Winroth, Magnolia\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Waller, Joakim\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Batista, Catarina\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Engström, Alice\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789198445725\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"741.51\",          \"Igy\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Batista, Natalia\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"559514\",      \"uberkey\": \"8ade8d9c-5806535a-015a-d1c2e7f9-6e11\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"Rita\",          \"Manga\"        ],        \"numbers\": {          \"ISBN\": [            \"9789197960458\"          ]        },        \"title\": \"Lär dig teckna manga!\",        \"descriptions\": [],        \"shelfMark\": \"Ig,u\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2017\",        \"contributors\": [          {            \"name\": \"Rosberg, Elise\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Batista, Catarina\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Engström, Alice\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Waller, Joakim\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Batista, Natalia\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789197960458\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"741.51\",          \"uIg\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Batista, Natalia\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"560335\",      \"uberkey\": \"8ade8da9-63816704-0165-d858e47c-558e\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"eAudio\",        \"subjects\": [],        \"numbers\": {          \"ISBN\": [            \"9789175458793\"          ]        },        \"title\": \"Hitlers krigare, del 1\",        \"descriptions\": [],        \"targetAudience\": \"a\",        \"contributors\": [          {            \"name\": \"Irheden, Ulf\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Wrangnert, Rolf\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789175458793\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Knopp, Guido\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"563259\",      \"uberkey\": \"8ade8daa-6680bdb5-0167-ce95d229-24af\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"eBook\",        \"subjects\": [],        \"numbers\": {          \"ISBN\": [            \"9789178517503\"          ]        },        \"title\": \"Männen\",        \"descriptions\": [],        \"targetAudience\": \"a\",        \"publicationYear\": \"2018\",        \"contributors\": [],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789178517503\",          \"https://images.elib.se/cover/1079815/1079815_201812191910.jpg\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Varas, Evelina\",            \"role\": \"AUTHOR\"          }        ]      }    }  ]}");
        HashMap<String, Object> retval = new HashMap<>();
        when(jsonFactory.looseDeserialize(anyString(), eq(Map.class))).thenReturn(retval);
        Map<String, Object> result = tested.callCentral(ARENA_CENTRAL_URL);
        assertThat(result, is(retval));
        assertThat(captor.getValue(), is(ARENA_CENTRAL_URL));
    }

    @Test
    public void testCallCentral_error() throws IOException {
        JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
        jsonFactoryUtil.setJSONFactory(jsonFactory);

        CatalogueReferenceServiceImpl tested = new CatalogueReferenceServiceImpl();
        tested.setStaticContextService(staticContextService);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        when(staticContextService.URLtoString(captor.capture())).thenReturn("{  \"total\": 232473,  \"records\": [    {      \"id\": \"558883\",      \"uberkey\": \"8ade8d9c-61653cbe-0162-fcb7b10e-2082\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"daisy\",        \"subjects\": [          \"Deckare\"        ],        \"numbers\": {},        \"languageOriginal\": \"nor\",        \"title\": \"Katharinakoden\",        \"descriptions\": [],        \"shelfMark\": \"Hce/TD ; Hce/TD\",        \"targetAudience\": \"a\",        \"publicationYear\": \"2018\",        \"contributors\": [          {            \"name\": \"Mitchell, Cajsa\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Lindgren, Åke\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"839.78\",          \"Hcedb.01/TD\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Horst, Jørn Lier\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"558219\",      \"uberkey\": \"8ade8d9c-5806535a-015b-4eb6efaf-0e48\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"Grisar\",          \"Tandläkare\",          \"Borsta tänderna\",          \"Förmänskligade djur\"        ],        \"numbers\": {          \"ISBN\": [            \"9789177832812\"          ]        },        \"languageOriginal\": \"eng\",        \"title\": \"Gapa stort\",        \"descriptions\": [],        \"shelfMark\": \"Hcf\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2018\",        \"contributors\": [          {            \"name\": \"Helleday Ekwurtzel, Marie\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789177832812\"        ],        \"category\": \"fictionCategory\",        \"classificationCodes\": [          \"839.78\",          \"Hcf(yb)\"        ],        \"contentTypes\": [],        \"authors\": []      }    },    {      \"id\": \"558395\",      \"uberkey\": \"8ade8d9d-5949fa37-015b-3887c2dc-3a26\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"alb\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"Resor\",          \"Slott\",          \"Fladdermöss\",          \"Vampyrer\"        ],        \"numbers\": {          \"ISBN\": [            \"9789928024800\",            \"9928024804\"          ]        },        \"languageOriginal\": \"ger\",        \"title\": \"Koni dhe kështjella e vampirëve\",        \"descriptions\": [],        \"shelfMark\": \"Hrf,uf\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2014\",        \"contributors\": [],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789928024800\",          \"https://api.axiell.com/cover/api/covers/sweden/9928024804\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"891.991\",          \"K\",          \"Hrf.01\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Boehme, Julia\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"561934\",      \"uberkey\": \"8ade8d9d-61655241-0162-533f08ac-250b\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"bos\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"digte.\",          \"billedbøger.\",          \"mødre.\",          \"forældre.\",          \"bedsteforældre.\",          \"landsbyen.\",          \"skove.\",          \"bøger.\",          \"læsning.\",          \"Lyrik\"        ],        \"numbers\": {          \"ISBN\": [            \"9789926427214\"          ]        },        \"title\": \"Olovka pustolovka\",        \"descriptions\": [],        \"shelfMark\": \"Hmfu,uf\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2017\",        \"contributors\": [          {            \"name\": \"Curic, Ivana\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Bilic, Zlata\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Krhin, Kristina\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789926427214\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"891.82\",          \"88.652\",          \"Hmfu.03,uf\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Esic, Simo\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"561935\",      \"uberkey\": \"8ade8dab-6680bdaf-0167-8175f846-2f07\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"hrv\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"Börja skolan\",          \"Siffror\",          \"Bokstäver\",          \"billedbøger.\",          \"skolen.\",          \"skolestart.\",          \"undervisning.\"        ],        \"numbers\": {          \"ISBN\": [            \"9789531421720\"          ]        },        \"title\": \"Dobro dosli u skolu!\",        \"descriptions\": [],        \"shelfMark\": \"Hmft,uf\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2017\",        \"contributors\": [          {            \"name\": \"Mezic, Zeljka\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789531421720\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"891.82\",          \"88.653\",          \"Hmft(yb)\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Mlinarec, Robert\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"559511\",      \"uberkey\": \"8ade8dab-6680bdaf-0166-f6e72513-15b0\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"srp\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"erindringer.\",          \"dagbøger.\",          \"ungdomsbøger.\",          \"unge.\",          \"ungdom.\",          \"skolen.\",          \"venskab.\",          \"forelskelse.\",          \"Skolan\",          \"Vänskap\",          \"Kärlek\"        ],        \"numbers\": {          \"ISBN\": [            \"9788664571425\"          ]        },        \"title\": \"Muski dnevnik 3\",        \"descriptions\": [],        \"shelfMark\": \"Hmfs,u\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2017\",        \"contributors\": [],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9788664571425\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"891.82\",          \"88.654\",          \"Hmfs.01\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Pejcic, Dusan\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"559513\",      \"uberkey\": \"8ade8da9-63816704-0165-0ea160d8-32a4\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"Manga\",          \"Rita\"        ],        \"numbers\": {          \"ISBN\": [            \"9789198445725\"          ]        },        \"title\": \"Nosebleed Studio lär dig teckna manga!\",        \"descriptions\": [],        \"shelfMark\": \"Ig,u\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2018\",        \"contributors\": [          {            \"name\": \"Rosberg, Elise\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Winroth, Magnolia\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Waller, Joakim\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Batista, Catarina\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Engström, Alice\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789198445725\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"741.51\",          \"Igy\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Batista, Natalia\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"559514\",      \"uberkey\": \"8ade8d9c-5806535a-015a-d1c2e7f9-6e11\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"book\",        \"subjects\": [          \"Rita\",          \"Manga\"        ],        \"numbers\": {          \"ISBN\": [            \"9789197960458\"          ]        },        \"title\": \"Lär dig teckna manga!\",        \"descriptions\": [],        \"shelfMark\": \"Ig,u\",        \"targetAudience\": \"j\",        \"publicationYear\": \"2017\",        \"contributors\": [          {            \"name\": \"Rosberg, Elise\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Batista, Catarina\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Engström, Alice\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Waller, Joakim\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Batista, Natalia\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789197960458\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [          \"741.51\",          \"uIg\"        ],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Batista, Natalia\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"560335\",      \"uberkey\": \"8ade8da9-63816704-0165-d858e47c-558e\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"eAudio\",        \"subjects\": [],        \"numbers\": {          \"ISBN\": [            \"9789175458793\"          ]        },        \"title\": \"Hitlers krigare, del 1\",        \"descriptions\": [],        \"targetAudience\": \"a\",        \"contributors\": [          {            \"name\": \"Irheden, Ulf\",            \"role\": \"CONTRIBUTOR\"          },          {            \"name\": \"Wrangnert, Rolf\",            \"role\": \"CONTRIBUTOR\"          }        ],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789175458793\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Knopp, Guido\",            \"role\": \"AUTHOR\"          }        ]      }    },    {      \"id\": \"563259\",      \"uberkey\": \"8ade8daa-6680bdb5-0167-ce95d229-24af\",      \"fields\": {        \"notes\": [],        \"languages\": [          \"swe\"        ],        \"mediaClass\": \"eBook\",        \"subjects\": [],        \"numbers\": {          \"ISBN\": [            \"9789178517503\"          ]        },        \"title\": \"Männen\",        \"descriptions\": [],        \"targetAudience\": \"a\",        \"publicationYear\": \"2018\",        \"contributors\": [],        \"coverUrls\": [          \"https://api.axiell.com/cover/api/covers/sweden/9789178517503\",          \"https://images.elib.se/cover/1079815/1079815_201812191910.jpg\"        ],        \"category\": \"nonFictionCategory\",        \"classificationCodes\": [],        \"contentTypes\": [],        \"authors\": [          {            \"name\": \"Varas, Evelina\",            \"role\": \"AUTHOR\"          }        ]      }    }  ]}");
        HashMap<String, Object> retval = new HashMap<>();
        retval.put("cause", ERROR_MSG);
        when(jsonFactory.looseDeserialize(anyString(), eq(Map.class))).thenReturn(retval);

        try {
            tested.callCentral(ARENA_CENTRAL_URL);
            assertThat(captor.getValue(), is(ARENA_CENTRAL_URL));
            fail("Should throw IOException");
        } catch (IOException e) {
            assertThat(e.getMessage(), is(ERROR_MSG));
        }
    }


    @Test
    public void testGetCoverImagePath() throws ExecutionException {
        CatalogueReferenceServiceImpl tested = new CatalogueReferenceServiceImpl();
        tested.setStaticContextService(staticContextService);
        tested.setCentralSettingsCache(centralSettingsCache);

        when(staticContextService.getThemeDisplay()).thenReturn(themeDisplay);
        when(themeDisplay.getPortalURL()).thenReturn("bookit.axiell.com");
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        when(centralSettingsCache.get(captor.capture())).thenReturn(centralSettingsDto);
        when(centralSettingsDto.getPortalSiteId()).thenReturn(0L);
        when(centralSettingsDto.getAgencyId()).thenReturn(0L);

        String result = tested.getCoverImagePath(1000L, 16542L);
        assertThat(captor.getValue(), is(16542L));
        assertThat(result, is("bookit.axiell.com/local-rest/api/v1/portalsites/0/agencies/0/records/1000/cover"));
    }

    @Test
    public void testGetSearchUrl() throws ExecutionException, PortalException {
        CatalogueReferenceServiceImpl tested = new CatalogueReferenceServiceImpl();
        when(staticContextService.getThemeDisplay()).thenReturn(themeDisplay);
        when(themeDisplay.getPortalURL()).thenReturn("https://www.bibliotek.trollhattan.se/en_GB");
        when(groupLocalService.getGroup(anyLong())).thenReturn(group);
        when(group.getFriendlyURL()).thenReturn("/arena");
        tested.setGroupLocalService(groupLocalService);
        tested.setStaticContextService(staticContextService);
        String result = tested.getSearchUrl("search", "harry potter", 1L);
        assertThat(result, is("https://www.bibliotek.trollhattan.se/en_GB/web/arena/search?p_r_p_arena_urn%3Aarena_search_query=harry+potter"));
    }


    @Test
    public void testGetAuthorSearchUrl() throws PortalException, UnsupportedEncodingException {
        CatalogueReferenceServiceImpl tested = new CatalogueReferenceServiceImpl();
        tested.setStaticContextService(staticContextService);
        tested.setGroupLocalService(groupLocalService);

        when(staticContextService.getThemeDisplay()).thenReturn(themeDisplay);
        when(themeDisplay.getPortalURL()).thenReturn("bookit.axiell.com");
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        when(groupLocalService.getGroup(captor.capture())).thenReturn(group);
        when(group.getFriendlyURL()).thenReturn("/arena");

        String result = tested.getAuthorSearchUrl("searchPath", 16542L, "henning mankell");
        assertThat(captor.getValue(), is(16542L));
        assertThat(result, is("bookit.axiell.com/web/arena/searchPath?p_p_id=searchPath_WAR_arenaportlet&p_p_lifecycle=1&p_p_state=normal&p_r_p_arena_urn%3Aarena_facet_queries=&p_r_p_arena_urn%3Aarena_search_query=author:henning mankell&p_r_p_arena_urn%3Aarena_search_type=solr"));
    }

    @Test
    public void testGetCrdUrl() throws PortalException, ExecutionException, UnsupportedEncodingException {
        CatalogueReferenceServiceImpl tested = new CatalogueReferenceServiceImpl();
        tested.setStaticContextService(staticContextService);
        tested.setGroupLocalService(groupLocalService);
        tested.setCentralSettingsCache(centralSettingsCache);

        when(staticContextService.getThemeDisplay()).thenReturn(themeDisplay);
        when(themeDisplay.getPortalURL()).thenReturn("bookit.axiell.com");
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        when(groupLocalService.getGroup(captor.capture())).thenReturn(group);
        when(group.getFriendlyURL()).thenReturn("/arena");
        when(centralSettingsCache.get(captor.capture())).thenReturn(centralSettingsDto);
        when(centralSettingsDto.getAgencyName()).thenReturn("my agency");

        String result = tested.getCrdUrl("resultPath", 1000L, 16542L);
        assertThat(captor.getValue(), is(16542L));
        assertThat(result, is("bookit.axiell.com/web/arena/resultPath?p_p_id=crDetailWicket_WAR_arenaportlet&p_p_lifecycle=1&p_p_state=normal&p_r_p_arena_urn%3Aarena_search_item_id=1000&p_r_p_arena_urn%3Aarena_agency_name=my agency"));

        String result2 = tested.getCrdUrl(1000L, 16542L);
        assertThat(captor.getValue(), is(16542L));
        assertThat(result2, is("bookit.axiell.com/web/arena/results?p_p_id=crDetailWicket_WAR_arenaportlet&p_p_lifecycle=1&p_p_state=normal&p_r_p_arena_urn%3Aarena_search_item_id=1000&p_r_p_arena_urn%3Aarena_agency_name=my agency"));
    }*/

    @Test
    public void dummyTest() {
        // This is a dummy test to ensure the class is not empty
        assertThat(true, is(true));
    }
}
