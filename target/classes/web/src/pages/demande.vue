<template>
  <VRow>
    <VCol
      cols="12"
      sm="12"
      md="12"
    >
      <VExpansionPanels variant="accordion">
        <VExpansionPanel v-if="result">
          <VExpansionPanelTitle> Soumettre une Demande </VExpansionPanelTitle>
          <VExpansionPanelText>
            <VForm
              ref="addDemand"
              @submit.prevent="addDemand"
              class="mt-1 mb-1"
            >
              <VRow>
                <VCol
                  cols="12"
                  md="6"
                >
                  <VTextField
                    v-model="demande.etudiant_id"
                    placeholder="ex : Nanyang Brice"
                    label="Etudiant"
                    density="compact"
                    persistent-placeholder
                    readonly=""
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="6"
                >
                  <VAutocomplete
                    v-model="demande.sous_categorie_id"
                    label="Sous Catégorie"
                    density="compact"
                    persistent-placeholder
                    :items="demandeList"
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="12"
                >
                  <VTextarea
                    v-model="description"
                    placeholder="Rien à signaler."
                    label="demande.description"
                    density="compact"
                    persistent-placeholder
                    rows="2"
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
                    Soumettre
                  </VBtn>
                </VCol>
              </VRow>
            </VForm>
          </VExpansionPanelText>
        </VExpansionPanel>

        <VExpansionPanel>
          <VExpansionPanelTitle> Liste des Demandes </VExpansionPanelTitle>

          <VExpansionPanelText>
            <VTextField
              v-model="search"
              placeholder="Rechercher ......"
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
                        <v-list-item-title v-if="result">
                          <VDialog
                            v-model="modifier"
                            persistent
                            class="v-dialog-sm"
                          >
                            <!-- Dialog Activator -->
                            <template #activator="{ props }">
                              <VBtn
                                variant="text"
                                @click="ready(item.raw)"
                                v-bind="props"
                              >
                                <VIcon icon="mdi-backup-outline"></VIcon>
                                Pièce Jointe
                                <VTooltip
                                  location="top"
                                  activator="parent"
                                >
                                  Importer une piece jointe
                                </VTooltip>
                              </VBtn>
                            </template>

                            <!-- Dialog Content -->
                            <VCard title="Ajouter une Pièce jointe">
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
                                      <VFileInput
                                        label="Sélectionnez le fichier"
                                        show-size
                                        counter
                                        prepend-icon=""
                                        prepend-inner-icon="mdi-folder-open-outline"
                                        density="compact"
                                        :rules="[rules.required]"
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
                                    Importer
                                  </VBtn>
                                </VCardActions>
                              </VForm>
                            </VCard>
                          </VDialog>
                        </v-list-item-title>

                        <v-list-item-title v-if="!result">
                          <!-- {{ item.raw.statut }} -->
                          <VBtn
                            variant="text"
                            size="small"
                            @click="prise_en_charge(item.raw)"
                          >
                            <b>
                              En charge
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
                              Prendre en charge la demande de l'étudiant {{ item.raw.etudiant.nom }}
                            </VTooltip>
                          </VBtn>
                        </v-list-item-title>

                        <v-list-item-title v-if="!result">
                          <VBtn
                            variant="text"
                            size="small"
                            color="success"
                            @click="desactiver(item.raw)"
                          >
                            <b>
                              Approuvée
                              <v-icon
                                class="mx-1"
                                aria-hidden="false"
                                >mdi-check-outline</v-icon
                              >
                            </b>

                            <VTooltip
                              location="top"
                              activator="parent"
                            >
                              finalisé la demande de l'étudiant {{ item.raw.etudiant.nom }}
                            </VTooltip>
                          </VBtn>
                        </v-list-item-title>

                        <v-list-item-title v-if="!result">
                          <VBtn
                            variant="text"
                            size="small"
                            color="error"
                            @click="piece_jointe_file(item.raw)"
                          >
                            <b>
                              Rejetter
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
                              Ajouter une pièce jointe
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
      { title: 'RESPONSABLE', key: 'personnel.nom' },
      { title: 'ETUDIANT', key: 'etudiant.nom' },
      { title: 'CATEGORIE', key: 'sousCategorie.categorie.nom' },
      { title: 'SOUS-CATEGORIE', key: 'sousCategorie.nom' },
      { title: 'DEPARTEMENT', key: 'sousCategorie.departement.nom' },
      { title: 'PIECE JOINTE', key: 'piecesJointes' },
      { title: 'STATUT', key: 'statut' },
      { title: 'ACTIONS', sortable: false, key: 'actions' },
    ]

    const resolveStatusVariant = statut => {
      if (statut === 'Demande receptionnée') {
        return { color: 'primary', text: 'Demande receptionnée' }
      } else if (statut === 'pris en charge') {
        return { color: 'warning', text: 'Prise en charge' }
      } else if (statut === 'finalisée') {
        return { color: 'success', text: 'Demande finalisée' }
      } else if (statut === 'rejettée') {
        return { color: 'error', text: 'Demande rejettée' }
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
      demandeList: [],
      personnelList: [],
      personnel: 1,
      id: '',
      nom: '',
      result: false,
      demande: {
        etudiant_id: '2-) NANYANG Brice',
        personnel_id: '',
        sous_categorie_id: '',
        statut: '',
      },

      upgrade: {
        etudiant: '',
        demande: '',
        piece_jointe: '',
      },

      update: {
        etudiant_id: '',
        personnel_id: '',
        sous_categorie_id: '',
        statut: '',
      },

      rules: {
        required: value => !!value || 'Désolé mais ce champ est obligatoire.',
      },
    }
  },

  created() {
    this.getDatas()
    this.getSousCategorie()

    let account = JSON.parse(localStorage.getItem('account'))
    if (account.created_by === 'etudiant') {
      this.result = true
    } else {
      this.result = false
    }
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
      if (account.created_by == 'etudiant') {
        this.student = false
      }
    }
  },

  methods: {
    async getSousCategorie() {
      try {
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
        }

        axios
          .get('allsCat')
          .then(response => {
            this.demandeList = response.data
            for (let i = 0; i < response.data.length; i++) {
              this.demandeList[i] = response.data[i].id + '-) ' + response.data[i].nom
            }
            console.log(this.demandeList)
          })
          .catch(error => {
            console.log(error)
          })
      } catch (error) {
        console.log(error)
      }
    },

    async ready(data) {
      console.log(data)
      this.id = data.id
      this.upgrade.etudiant = data.etudiant.id + '-) ' + data.etudiant.nom
      this.upgrade.demande = data.id
    },

    async getDatas() {
      try {
        axios
          .get('allDem')
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

    async addDemand() {
      try {
        const valid = this.$refs.addDemand.validate()
        let sousCategorie = this.demande.sous_categorie_id.split('-)')
        let etudiant = this.demande.etudiant_id.split('-)')
        console.log(valid)

        if (valid) {
          Swal.fire({
            title: 'Êtes vous sûres ?',
            html: 'Souhaitez vous transmettre cette demande ?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Je le souhaite',
            denyButtonText: `Annuler`,
          }).then(result => {
            if (result.isConfirmed) {
              axios
                .post('addDem/' + etudiant[0] + '/' + sousCategorie[0], {
                  etudiant_id: etudiant[0],
                  personnel_id: this.demande.personnel_id,
                  sous_categorie_id: sousCategorie[0],
                  statut: 'Demande receptionnée',
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
      this.update.etudiant_id = data.etudiant.id + '-) ' + data.etudiant.nom
      this.update.personnel_id = data
      this.update.sous_categorie_id = data.sous_categorie_id
      console.log(this.data)
    },

    async prise_en_charge(data) {
      Swal.fire({
        title: 'Êtes vous sûres ?',
        html: "Souhaitez vous prendre en charge la demande de l'étudiant <b>" + data.etudiant.nom.toUpperCase() + ' ?',
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: 'Je le souhaite',
        denyButtonText: `Annuler`,
        cancelButtonText: `Annuler`,
      }).then(result => {
        if (result.isConfirmed) {
          axios.put('prisEnchargeDem/' + data.id + '/' + this.personnel).then(response => {
            this.toast.info(response.data, {
              position: 'top-right',
              duration: 7500,
            })
            this.getDatas()
          })
        }
      })
    },

    async approuver(data) {
      Swal.fire({
        title: 'Êtes vous sûres ?',
        html: "Souhaitez vous approuver la demande de l'étudiant <b>" + data.etudiant.nom.toUpperCase() + ' ?',
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: 'Je le souhaite',
        denyButtonText: `Annuler`,
        cancelButtonText: `Annuler`,
      }).then(result => {
        if (result.isConfirmed) {
          axios.put('approuverDem/' + data.id + '/' + this.personnel).then(response => {
            this.toast.info(response.data, {
              position: 'top-right',
              duration: 7500,
            })
            this.getDatas()
          })
        }
      })
    },

    async piece_jointe(data) {
      Swal.fire({
        title: 'Êtes vous sûres ?',
        html:
          'Souhaitez vous ajouter une piece jointe à votre demande de <b>' + data.etudiant.nom.toUpperCase() + '</b> ?',
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

    async rejetter(data) {
      Swal.fire({
        title: 'Êtes vous sûres ?',
        html: "Souhaitez vous rejetter la demande de l'étudiant <b>" + data.etudiant.nom.toUpperCase() + ' ?',
        showDenyButton: true,
        showCancelButton: false,
        confirmButtonText: 'Je le souhaite',
        denyButtonText: `Annuler`,
        cancelButtonText: `Annuler`,
      }).then(result => {
        if (result.isConfirmed) {
          axios.put('rejetterDem/' + data.id + '/' + this.personnel).then(response => {
            this.toast.info(response.data, {
              position: 'top-right',
              duration: 7500,
            })
            this.getDatas()
          })
        }
      })
    },

    async onImageUpload() {
      let file = this.$refs.uploadImage.files[0]
      this.formData = new FormData()
      this.formData.append('file', file)
    },
  },
}
</script>
