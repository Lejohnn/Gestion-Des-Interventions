<template>
  <VRow>
    <VCol
      cols="12"
      sm="12"
      md="12"
    >
      <VExpansionPanels variant="accordion">
        <VExpansionPanel>
          <VExpansionPanelTitle> Ajouter un personnel </VExpansionPanelTitle>
          <VExpansionPanelText>
            <VForm
              ref="addPersone"
              @submit.prevent="addPersone"
              class="mt-1 mb-1"
            >
              <VRow>
                <VCol
                  cols="12"
                  md="4"
                >
                  <VTextField
                    v-model="personnel.nom"
                    placeholder="ex : Nanyang Brice"
                    label="Nom & Prénom"
                    density="compact"
                    :rules="[rules.required]"
                    persistent-placeholder
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="4"
                >
                  <VTextField
                    v-model="personnel.login"
                    placeholder="ex : OppenSource25#"
                    label="Login"
                    density="compact"
                    :rules="[rules.required]"
                    persistent-placeholder
                    :type="isPasswordVisible1 ? 'text' : 'password'"
                    :append-inner-icon="isPasswordVisible1 ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
                    @click:append-inner="isPasswordVisible1 = !isPasswordVisible1"
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="4"
                >
                  <VTextField
                    v-model="personnel.password"
                    label="Password"
                    :rules="[rules.required]"
                    density="compact"
                    persistent-placeholder
                    :type="isPasswordVisible ? 'text' : 'password'"
                    :append-inner-icon="isPasswordVisible ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
                    @click:append-inner="isPasswordVisible = !isPasswordVisible"
                    placeholder="***********"
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="4"
                >
                  <VTextField
                    v-model="personnel.email"
                    placeholder="ex : Brice.nanyang@in....jean.org"
                    label="Email"
                    density="compact"
                    :rules="[rules.required]"
                    persistent-placeholder
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="4"
                >
                  <VTextField
                    v-model="personnel.telephone"
                    placeholder="ex : 657807309"
                    label="Téléphone"
                    density="compact"
                    :rules="[rules.required]"
                    persistent-placeholder
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="4"
                >
                  <VTextField
                    v-model="personnel.poste"
                    placeholder="ex : Ingénieur Support"
                    label="Poste"
                    :rules="[rules.required]"
                    density="compact"
                    persistent-placeholder
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="12"
                >
                  <VAutocomplete
                    v-model="personnel.departement_id"
                    label="Département"
                    density="compact"
                    persistent-placeholder
                    placeholder="Selectionnez le département"
                    :items="departementList"
                    :rules="[rules.required]"
                    clearable
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="2"
                >
                  <VBtn
                    type="submit"
                    block
                  >
                    Enregistrer
                  </VBtn>
                </VCol>
              </VRow>
            </VForm>
          </VExpansionPanelText>
        </VExpansionPanel>

        <VExpansionPanel>
          <VExpansionPanelTitle> Liste du personnel </VExpansionPanelTitle>
          <VExpansionPanelText>
            <VTextField
              v-model="search"
              placeholder="Recherchez quelque chose ......"
              label="Search"
              density="compact"
              class="mb-2"
              clearable
              persistent-placeholder
            />
            <VDataTable
              :headers="headers"
              :items="personnelList"
              :search="search"
              :items-per-page="5"
              density="compact"
            >
              <!-- status -->
              <template #item.statut="{ item }">
                <VChip
                  :color="resolveStatusVariant(item.raw.statut).color"
                  class="font-weight-medium"
                  size="small"
                >
                  {{ resolveStatusVariant(item.raw.statut).text }}
                </VChip>
              </template>

              <template #item.actions="{ item }">
                <div class="d-flex gap-1">
                  <v-menu>
                    <template v-slot:activator="{ props }">
                      <v-btn
                        icon="mdi-cog-outline"
                        v-bind="props"
                        variant="text"
                        size="small"
                        color="primary"
                      ></v-btn>
                    </template>

                    <v-list>
                      <v-list-item>
                        <v-list-item-title>
                          <VDialog
                            v-model="modifier"
                            persistent
                            class="v-dialog-sm"
                          >
                            <!-- Dialog Activator -->
                            <template #activator="{ props }">
                              <VBtn
                                variant="text"
                                @click="open(item.raw)"
                                v-bind="props"
                                size="small"
                                collor=""
                              >
                                Modifier
                                <VIcon
                                  icon="mdi-notebook-edit-outline"
                                  class="mx-1"
                                ></VIcon>
                                <VTooltip
                                  location="top"
                                  activator="parent"
                                >
                                  Modifier le département {{ item.raw.nom }}
                                </VTooltip>
                              </VBtn>
                            </template>

                            <!-- Dialog Content -->
                            <VCard title="Modifier une Catégorie">
                              <VForm
                                ref="updatePers"
                                @submit.prevent="updatePers"
                                class="mt-1 mb-1"
                              >
                                <DialogCloseBtn
                                  variant="text"
                                  size="small"
                                  @click="modifier = false"
                                />

                                <VCardText>
                                  <VRow>
                                    <VCol
                                      cols="12"
                                      md="12"
                                    >
                                      <VTextField
                                        v-model="update.nom"
                                        placeholder="ex : Nanyang Brice"
                                        label="Nom & Prénom"
                                        density="compact"
                                        :rules="[rules.required]"
                                        persistent-placeholder
                                      />
                                    </VCol>

                                    <VCol
                                      cols="12"
                                      md="12"
                                    >
                                      <VTextField
                                        v-model="update.login"
                                        placeholder="ex : "
                                        label="Login"
                                        density="compact"
                                        :rules="[rules.required]"
                                        persistent-placeholder
                                      />
                                    </VCol>

                                    <VCol
                                      cols="12"
                                      md="12"
                                    >
                                      <VTextField
                                        v-model="update.password"
                                        placeholder="ex : "
                                        label="Password"
                                        :rules="[rules.required]"
                                        density="compact"
                                        persistent-placeholder
                                      />
                                    </VCol>

                                    <VCol
                                      cols="12"
                                      md="12"
                                    >
                                      <VTextField
                                        v-model="update.email"
                                        placeholder="ex : Brice.nanyang@in....jean.org"
                                        label="Email"
                                        density="compact"
                                        :rules="[rules.required]"
                                        persistent-placeholder
                                      />
                                    </VCol>

                                    <VCol
                                      cols="12"
                                      md="12"
                                    >
                                      <VTextField
                                        v-model="update.telephone"
                                        placeholder="ex : 657807309"
                                        label="Téléphone"
                                        density="compact"
                                        :rules="[rules.required]"
                                        persistent-placeholder
                                      />
                                    </VCol>

                                    <VCol
                                      cols="12"
                                      md="12"
                                    >
                                      <VAutocomplete
                                        v-model="update.departement_id"
                                        label="Département"
                                        density="compact"
                                        persistent-placeholder
                                        placeholder="Selectionnez le département"
                                        :items="departementList"
                                        :rules="[rules.required]"
                                        clearable
                                      />
                                    </VCol>
                                  </VRow>
                                </VCardText>

                                <VCardActions>
                                  <VSpacer />
                                  <VBtn
                                    color="error"
                                    variant="outlined"
                                    @click="modifier = false"
                                  >
                                    Annuler
                                  </VBtn>
                                  <VBtn
                                    color="primary"
                                    variant="outlined"
                                    type="submit"
                                  >
                                    Modifier
                                  </VBtn>
                                </VCardActions>
                              </VForm>
                            </VCard>
                          </VDialog>
                        </v-list-item-title>

                        <v-list-item-title :class="item.raw.statut != 'inactif' ? 'd-none' : ''">
                          <!-- {{ item.raw.statut }} -->
                          <VBtn
                            variant="text"
                            size="small"
                            color="success"
                            @click="reactiver(item.raw)"
                          >
                            <b>
                              Réactivé
                              <v-icon
                                class="mx-1"
                                aria-hidden="false"
                                >mdi-check-decagram-outline</v-icon
                              >
                            </b>
                            <VTooltip
                              location="top"
                              activator="parent"
                            >
                              Réactivé le compte de l'étudiant {{ item.raw.nom }}
                            </VTooltip>
                          </VBtn>
                        </v-list-item-title>

                        <v-list-item-title :class="item.raw.statut != 'actif' ? 'd-none' : ''">
                          <VBtn
                            variant="text"
                            size="small"
                            color="error"
                            @click="desactiver(item.raw)"
                          >
                            <b>
                              Désactivé
                              <v-icon
                                class="mx-1"
                                aria-hidden="false"
                                >mdi-close-outline</v-icon
                              >
                            </b>

                            <VTooltip
                              location="top"
                              activator="parent"
                            >
                              Désactivé le compte de l'étudiant {{ item.raw.nom }}
                            </VTooltip>
                          </VBtn>
                        </v-list-item-title>
                      </v-list-item>
                    </v-list>
                  </v-menu>
                </div>
              </template>
            </VDataTable>
          </VExpansionPanelText>
        </VExpansionPanel>
      </VExpansionPanels>
    </VCol>
  </VRow>
</template>

<script>
import axios from 'axios'
import Swal from 'sweetalert2'
import { VDataTable } from 'vuetify/labs/VDataTable'
import { useToast } from 'vue-toast-notification'
import 'vue-toast-notification/dist/theme-sugar.css'

export default {
  components: {
    VDataTable,
  },

  setup() {
    const search = ref('')
    const toast = useToast()
    const ajouter = ref(false)
    const modifier = ref(false)
    const headers = [
      { title: 'ID', sortable: false, key: 'id' },
      { title: 'DEPARTEMENT', key: 'departement.nom' },
      { title: 'NOMS', key: 'nom' },
      { title: 'EMAIL', key: 'email' },
      { title: 'TELEPHONE', key: 'telephone' },
      { title: 'POSTE', key: 'poste' },
      { title: 'STATUT', key: 'statut' },
      { title: 'ACTIONS', sortable: false, key: 'actions' },
    ]

    const resolveStatusVariant = statut => {
      if (statut === 'actif') {
        return { color: 'success', text: 'Actif' }
      } else {
        return { color: 'error', text: 'Inactif' }
      }
    }

    const isPasswordVisible1 = ref(false)
    const isPasswordVisible = ref(false)

    return {
      toast,
      search,
      ajouter,
      headers,
      modifier,
      resolveStatusVariant,
      isPasswordVisible1,
      isPasswordVisible,
    }
  },

  data() {
    return {
      search: '',
      departementList: [],
      personnelList: [],
      id: '',
      nom: '',
      student:'',
      personnel: {
        departement_id: '',
        email: '',
        login: (Math.random() + 1).toString(36).substring(6),
        nom: '',
        password: (Math.random() + 1).toString(36).substring(4),
        telephone: '',
        poste: '',
      },

      update: {
        departement_id: '',
        email: '',
        login: '',
        nom: '',
        password: '',
        telephone: '',
        poste: '',
      },

      rules: {
        required: value => !!value || 'Désolé mais ce champ est obligatoire.',
      },
    }
  },

  created() {
    this.getDatas()
    this.getDepartement()

    

    let account = JSON.parse(localStorage.getItem('account'))
    console.log(account)
    if (!account) {
      Swal.fire({
        icon: 'warning',
        text: 'Attention, cette connexion est illégitime.',
        toast: true,
        position: 'top-end',
        showCancelButton: false,
        showConfirmButton: false,
        timer: 5000,
        timerProgressBar: true,
      })
      this.$router.push('/login')
    } else {
      if (account.created_by != 'etudiant') {
        this.student = false
      }
    }
  },

  methods: {
    async getDepartement() {
      try {
        axios
          .get('allDep')
          .then(response => {
            this.departementList = response.data
            for (let i = 0; i < response.data.length; i++) {
              this.departementList[i] = response.data[i].id + '-) ' + response.data[i].nom
            }
            console.log(this.departementList)
          })
          .catch(error => {
            console.log(error)
          })
      } catch (error) {
        console.log(error)
      }
    },

    async getDatas() {
      try {
        axios
          .get('allPers')
          .then(response => {
            this.personnelList = response.data
          })
          .catch(error => {
            console.log(error)
          })
      } catch (error) {
        console.log(error)
      }
    },

    async addPersone() {
      try {
        const valid = this.$refs.addPersone.validate()
        let departement = this.personnel.departement_id.split('-)')
        console.log(valid)
        if (valid) {
          Swal.fire({
            title: 'Êtes vous sûres ?',
            html: 'Souhaitez vous enregistrer le personnel <b>' + this.personnel.nom.toUpperCase() + '<b> ?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Je le souhaite',
            denyButtonText: `Annuler`,
          }).then(result => {
            if (result.isConfirmed) {
              axios
                .post('addPersone/' + departement[0], {
                  departement_id: this.personnel.departement_id,
                  nom: this.personnel.nom,
                  email: this.personnel.email,
                  telephone: this.personnel.telephone,
                  login: this.personnel.login,
                  password: this.personnel.password,
                  statut: 'actif',
                  poste: this.personnel.poste,
                })
                .then(response => {
                  this.toast.info(response.data, {
                    position: 'top-right',
                    duration: 7500,
                  })
                  this.getDatas()
                })
            }
          })
        } else {
          this.toast.error('Données abscentes ou incorrectes', { position: 'top-right', duration: 7500 })
        }
      } catch (error) {
        console.log(error)
      }
    },

    async open(data) {
      this.modifier = true
      this.id = data.id
      this.update.email = data.email
      this.update.login = data.login
      this.update.nom = data.nom
      this.update.password = data.password
      this.update.telephone = data.telephone
      this.update.poste = data.poste
      console.log(data)
    },

    async updatePers() {
      try {
        const valid = this.$refs.updatePers.validate()
        console.log(valid)
        if (valid) {
          this.modifier = false
          let personne = this.id
          let departement = this.update.departement_id.split('-)')
          Swal.fire({
            title: 'Êtes vous sûres ?',
            html: 'Souhaitez vous modifier ce personnel  en : <b>' + this.update.nom.toUpperCase() + '<b> ?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Je le souhaite',
            denyButtonText: `Annuler`,
          }).then(result => {
            if (result.isConfirmed) {
              axios
                .put('updatePers/' + this.id + '/' + departement[0], {
                  departement_id: this.update.departement_id,
                  email: this.update.email,
                  login: this.update.login,
                  nom: this.update.nom,
                  poste: this.update.poste,
                  password: this.update.password,
                  telephone: this.update.telephone,
                })
                .then(response => {
                  this.toast.info(response.data, {
                    position: 'top-right',
                    duration: 7500,
                  })
                  this.modifier = false
                  this.getDatas()
                })
            }
          })
        } else {
          this.toast.error('Données abscentes ou incorrectes', { position: 'top-right', duration: 7500 })
        }
      } catch (error) {
        console.log(error)
      }
    },

    async desactiver(data) {
      Swal.fire({
        title: 'Êtes vous sûres ?',
        html: 'Souhaitez vous désactivé le compte du personnel <b>' + data.nom.toUpperCase() + ' ?',
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: 'Je le souhaite',
        denyButtonText: `Annuler`,
        cancelButtonText: `Annuler`,
      }).then(result => {
        if (result.isConfirmed) {
          axios.put('offPers/' + data.id).then(response => {
            this.toast.info(response.data, {
              position: 'top-right',
              duration: 7500,
            })
            this.modifier = false
            this.getDatas()
          })
        }
      })
    },

    async reactiver(data) {
      Swal.fire({
        title: 'Êtes vous sûres ?',
        html: 'Souhaitez vous réactivé le compte du personnel <b>' + data.nom.toUpperCase() + ' ?',
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: 'Je le souhaite',
        denyButtonText: `Annuler`,
        cancelButtonText: `Annuler`,
      }).then(result => {
        if (result.isConfirmed) {
          axios.put('onPers/' + data.id).then(response => {
            this.toast.info(response.data, {
              position: 'top-right',
              duration: 7500,
            })
            this.modifier = false
            this.getDatas()
          })
        }
      })
    },
  },
}
</script>
