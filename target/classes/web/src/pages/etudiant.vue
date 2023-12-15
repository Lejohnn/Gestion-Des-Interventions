<template>
  <VRow >
    <VCol
      cols="12"
      sm="12"
      md="12"
    >
      <VExpansionPanels variant="accordion">
        <VExpansionPanel>
          <VExpansionPanelTitle> Ajouter un Etudiant </VExpansionPanelTitle>
          <VExpansionPanelText>
            <VForm
              ref="addEtu"
              @submit.prevent="addEtu"
              class="mt-1 mb-1"
            >
              <VRow>
                <VCol
                  cols="12"
                  md="4"
                >
                  <VTextField
                    v-model="etudiant.nom"
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
                    v-model="etudiant.login"
                    placeholder="ex : "
                    label="Login"
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
                    v-model="etudiant.password"
                    placeholder="ex : "
                    label="Password"
                    :rules="[rules.required]"
                    density="compact"
                    persistent-placeholder
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="4"
                >
                  <VTextField
                    v-model="etudiant.email"
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
                    v-model="etudiant.telephone"
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
                    v-model="etudiant.matricule"
                    placeholder="ex : 2223i393"
                    label="Matricule"
                    :rules="[rules.required]"
                    density="compact"
                    persistent-placeholder
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
          <VExpansionPanelTitle> Liste des Etudiants </VExpansionPanelTitle>
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
              :items="etudiantList"
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
                                <b>
                                  Modifier
                                  <VIcon
                                    icon="mdi-notebook-edit-outline"
                                    class="mx-1"
                                  ></VIcon>
                                </b>
                                <VTooltip
                                  location="top"
                                  activator="parent"
                                >
                                  Modifier l'étudiant {{ item.raw.nom }}
                                </VTooltip>
                              </VBtn>
                            </template>

                            <!-- Dialog Content -->
                            <VCard title="Modifier une Catégorie">
                              <VForm
                                ref="updateCat"
                                @submit.prevent="updateCat"
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
                                      <VTextField
                                        v-model="update.matricule"
                                        placeholder="ex : 2223i393"
                                        label="Matricule"
                                        :rules="[rules.required]"
                                        density="compact"
                                        persistent-placeholder
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
      { title: 'MATRICULE', key: 'matricule' },
      { title: 'NOMS', key: 'nom' },
      { title: 'EMAIL', key: 'email' },
      { title: 'TELEPHONE', key: 'telephone' },
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

    const isPasswordVisible = ref(false)

    return {
      toast,
      search,
      ajouter,
      headers,
      modifier,
      resolveStatusVariant,
      isPasswordVisible,
    }
  },

  data() {
    return {
      search: '',
      etudiantList: [],
      id: '',
      nom: '',
      student: '',
      etudiant: {
        matricule: '',
        email: '',
        login: (Math.random() + 1).toString(36).substring(5),
        nom: '',
        password: (Math.random() + 1).toString(36).substring(3),
        telephone: '',
      },

      update: {
        matricule: '',
        email: '',
        login: '',
        nom: '',
        password: '',
        telephone: '',
      },

      rules: {
        required: value => !!value || 'Désolé mais ce champ est obligatoire.',
      },
    }
  },

  created() {
    this.getDatas()
    

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
    async getDatas() {
      try {
        axios
          .get('allEtu')
          .then(response => {
            this.etudiantList = response.data
          })
          .catch(error => {
            console.log(error)
          })
      } catch (error) {
        console.log(error)
      }
    },

    async addEtu() {
      try {
        const valid = this.$refs.addEtu.validate()
        console.log(valid)
        if (valid) {
          Swal.fire({
            title: 'Êtes vous sûres ?',
            html: "Souhaitez vous enregistrer l'étudiant <b>" + this.etudiant.nom + '<b> ?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Je le souhaite',
            denyButtonText: `Annuler`,
          }).then(result => {
            if (result.isConfirmed) {
              axios
                .post('addEtu', {
                  matricule: this.etudiant.matricule,
                  email: this.etudiant.email,
                  login: this.etudiant.login,
                  nom: this.etudiant.nom,
                  password: this.etudiant.password,
                  statut: 'actif',
                  telephone: this.etudiant.telephone,
                })
                .then(response => {
                  this.toast.info(response.data, {
                    position: 'top-right',
                    duration: 7500,
                  })
                  this.matricule = ''
                  this.email = ''
                  this.login = ''
                  this.nom = ''
                  this.password = ''
                  this.statut = ''
                  this.telephone = ''
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
      this.update.matricule = data.matricule
      this.update.email = data.email
      this.update.login = data.login
      this.update.nom = data.nom
      this.update.password = data.password
      this.update.telephone = data.telephone
    },

    async updateEtu() {
      try {
        const valid = this.$refs.updateEtu.validate()
        console.log(valid)
        if (valid) {
          this.modifier = false
          Swal.fire({
            title: 'Êtes vous sûres ?',
            html: 'Souhaitez vous modifier cette étudiant  en : <b>' + this.update.nom + '<b> ?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Je le souhaite',
            denyButtonText: `Annuler`,
          }).then(result => {
            if (result.isConfirmed) {
              axios
                .put('updateEtu/' + this.id, {
                  matricule: this.update.matricule,
                  email: this.update.email,
                  login: this.update.login,
                  nom: this.update.nom,
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
        html: "Souhaitez vous désactivé le compte de l'étudiant <b>" + data.nom.toUpperCase() + ' ?',
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: 'Je le souhaite',
        denyButtonText: `Annuler`,
        cancelButtonText: `Annuler`,
      }).then(result => {
        if (result.isConfirmed) {
          axios.put('offEtu/' + data.id).then(response => {
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
        html: "Souhaitez vous réactivé le compte de l'étudiant <b>" + data.nom.toUpperCase() + ' ?',
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: 'Je le souhaite',
        denyButtonText: `Annuler`,
        cancelButtonText: `Annuler`,
      }).then(result => {
        if (result.isConfirmed) {
          axios.put('onEtu/' + data.id).then(response => {
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
